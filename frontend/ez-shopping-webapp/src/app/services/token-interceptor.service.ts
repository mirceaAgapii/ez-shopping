import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, switchMap, take } from 'rxjs/operators';
import { JWTTokenService } from './jwttoken.service';
import { LocalStorageService } from './local-storage.service';
import { UserRestService } from './user.rest.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  isRefreshing: boolean = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private jwtService: JWTTokenService,
    private userRestService: UserRestService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(this.setAuthHeader(req))/*.pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse && error.status === 401) {
          console.log('in intercept 1 ' + error.status);
         
      
      
         return this.handle401Error(req, next);
        } else {
          console.log('in intercept 1');
          return throwError(error);
        }
      })
    );*/
  }

 /* handle401Error(req: HttpRequest<any>, next: HttpHandler) {
    //if(!this.isRefreshing) {
      console.log('in 401 1');
      //this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const token = this.jwtService.getRefreshToken();

      if (token) {
       this.userRestService.refreshToken().pipe(
        switchMap((token: any) => {
          this.isRefreshing = false;

          this.jwtService.saveAccessToken(token.accessToken);
          this.refreshTokenSubject.next(token.accessToken);
          
          return next.handle(this.setAuthHeader(req));
        }),
        catchError((err) => {
          console.log('in new token 1');
          this.isRefreshing = false;
          
          this.jwtService.signOut();
          return throwError(err);
        }));
      }
    //}

    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.setAuthHeader(req)))
    );
  }*/
    

  setAuthHeader(req: HttpRequest<any>): HttpRequest<any> {
    /*if(req.headers.has('Authorization')){
      return req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getRefreshToken()}`
        }
      })
    } else {*/
      if(this.jwtService.getAccessToken()) {
        return req.clone({
          setHeaders: {
            Authorization: `Bearer ${this.jwtService.getAccessToken()}`
          }
        })
      } else {
        return req;
      }
     
    //}
  }
}
