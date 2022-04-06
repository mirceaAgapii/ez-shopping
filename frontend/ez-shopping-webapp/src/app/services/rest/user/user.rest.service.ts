import { Injectable } from '@angular/core';
import { catchError, switchMap, timeout } from 'rxjs/internal/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams, HttpResponse, HttpRequest, HttpHandler } from '@angular/common/http';
import { BehaviorSubject, concat, Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../../../Model/User';
import { environment } from 'src/environments/environment';
import { JWTTokenService } from '../../auth/jwttoken.service';
import { LocalStorageService } from '../../auth/storage/local-storage.service';
import { Router } from '@angular/router';
import { UserService } from '../../user/user.service';
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
        environment.restUrl + '/login', 
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
    return this.http.post<any>(environment.restUrl + '/users/save', body);
  }

  updateUser(user: User) {
    const body = {
      id : user.id,
      username : user.username,
      password : user.password,
      email : user.email,
      role : user.role
    }
    this.http.patch<any>(environment.restUrl + '/users/user', body).subscribe({
      next: data => {
        console.log('successful update');
      },
      error: error => {
        console.log(error);
      }
    })
  }

  refreshToken()  {
     return this.http.get<HttpResponse<Object>>(environment.restUrl + '/users/token/refresh', {
       headers: this.getRefreshTokenHeader(),
       observe: 'response'
      });
  }

  getAllUsers(): Observable<Array<User>>
  {
    return this.http.get<Array<User>>(environment.restUrl + '/users');

  }

  getUserById(id: string) {
    return this.http.get<User>(environment.restUrl + '/users/user?id=' + id);
  }

  deleteUsers(user: User) {
    console.log(environment.restUrl + '/users/user?id=' + user.id);
    this.http.delete<any>(environment.restUrl + '/users/user?id=' + user.id).subscribe(
      data => console.log(data)
    );
  }

  updatePassword(oldPass: string, newPass: string, userId: string) {
    const body = {
      oldPassword : oldPass,
      newPassword : newPass
    }

    return this.http.patch<any>(environment.restUrl + '/users/user/password?id=' + userId, body);
  }

  private getRefreshTokenHeader(): HttpHeaders {
    let header = new HttpHeaders().set(
      "Authorization",
      'Bearer ' + this.jwtService.getRefreshToken()
    );
    return header;
  }
}
