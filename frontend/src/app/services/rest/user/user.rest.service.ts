import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../Model/User';
import { environment } from 'src/environments/environment';
import { JWTTokenService } from '../../auth/jwttoken.service';
import { Router } from '@angular/router';
import {AuthorizationService} from "../../auth/authorization.service";



@Injectable({
  providedIn: 'root'
})
export class UserRestService {
  constructor(private http: HttpClient,
    private jwtService: JWTTokenService,
    private router: Router,
    private authenticationService: AuthorizationService) {

  }

  login(username: string, password: string) {
    let body = new HttpParams();
    body = body.set('username', username);
    body = body.set('password', password);
    return this.http.post<HttpResponse<Object>>(
        environment.restUrl + environment.api + '/login',
        body,
        {observe: 'response'}
      );
  }

  saveTokens(data: HttpResponse<Object>) {
    var token: string | null;
    var refresh_token: string | null;
    token = data.headers.get("access_token");
    refresh_token = data.headers.get("refresh_token");
    if(token && refresh_token) {
      this.jwtService.saveAccessToken(token);
      this.jwtService.saveRefreshToken(refresh_token);
    }
  }

  register(user: User) {
    const body = {
      username : user.username,
      password : user.password,
      email : user.email
    }
    return this.http.post<any>(environment.restUrl + environment.api + '/users/save', body);
  }

  updateUser(user: User) {
    const body = {
      id : user.id,
      username : user.username,
      password : user.password,
      email : user.email,
      role : user.role
    }
    return this.http.patch<any>(environment.restUrl + environment.api + '/users/user', body);
  }

  refreshToken()  {
     return this.http.get<HttpResponse<Object>>(environment.restUrl + environment.api + '/users/token/refresh', {
       headers: this.getRefreshTokenHeader(),
       observe: 'response'
      });
  }

  getAllUsers(): Observable<Array<User>>
  {
    return this.http.get<Array<User>>(environment.restUrl + environment.api + '/users');

  }

  getUserById(id: string) {
    return this.http.get<User>(environment.restUrl + environment.api + '/users/user?id=' + id);
  }

  deleteUsers(user: User) {
    console.log(environment.restUrl + environment.api + '/users/user?id=' + user.id);
    return this.http.delete<any>(environment.restUrl + environment.api + '/users/user?id=' + user.id);
  }

  updatePassword(oldPass: string, newPass: string, userId: string) {
    const body = {
      oldPassword : oldPass,
      newPassword : newPass
    }

    return this.http.patch<any>(environment.restUrl + environment.api + '/users/user/password?id=' + userId, body);
  }

  private getRefreshTokenHeader(): HttpHeaders {
    let header = new HttpHeaders().set(
      "Authorization",
      'Bearer ' + this.jwtService.getRefreshToken()
    );
    return header;
  }
}
