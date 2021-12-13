import { Injectable } from '@angular/core';
import { catchError, switchMap, timeout } from 'rxjs/internal/operators';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, concat, Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../Model/User';
import { environment } from 'src/environments/environment';
import { JWTTokenService } from './jwttoken.service';
import { LocalStorageService } from './local-storage.service';
import { Router } from '@angular/router';
import { UserService } from './user.service';


@Injectable({
  providedIn: 'root'
})
export class UserRestService {
  constructor(private http: HttpClient,
    private jwtTokenService: JWTTokenService,
    private localStorageService: LocalStorageService,
    private router: Router, private userService: UserService) { 

  }

  login(username: string, password: string) {


    let body = new HttpParams();
    body = body.set('username', username);
    body = body.set('password', password);
    this.http.post(environment.restUrl + '/login', body, {observe: 'response'})
    .subscribe(
      data => {
        this.saveTokens(data);
        this.router.navigate(['']);
        let user: User = new User();
        user.username = this.jwtTokenService.getUser();
        user.role = this.jwtTokenService.getUserRoles();
        this.userService.setCurrentUser(user);
      },
      error => {
        throwError(error);
      }
    )
  }

  saveTokens(data: HttpResponse<Object>) {
    var token: string | null;
    var refresh_token: string | null;
    token = data.headers.get("access_token");
    refresh_token = data.headers.get("refresh_token");
    if(token && refresh_token) {
      this.jwtTokenService.saveAccessToken(token);
      this.jwtTokenService.saveRefreshToken(refresh_token);
    }
  }

  register(user: User) {
    const body = {
      username : user.username,
      password : user.password,
      email : user.email
    }
    this.http.post<any>(environment.restUrl + '/users/save', body).subscribe({
      next: data => {
        console.log('success');
        this.router.navigate(['/login']);
      },
      error: error => {
        console.log(error);
      }
    });
  }

  /*refreshToken()  {
     return this.http.post<HttpResponse<Object>>(environment.restUrl + '/users/token/refresh', {
       headers: this.getRefreshTokenHeader()
      })
  }*/

  getAllUsers(): Observable<Array<User>> 
  {
    return this.http.get<Array<User>>(environment.restUrl + '/users');

  }

 /* private getRefreshTokenHeader(): HttpHeaders {
    let header = new HttpHeaders().set(
      "Authorization",
      'Bearer ' + this.jwtTokenService.getRefreshToken()
    );
    return header;
  }*/


}
