import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MessageService } from 'primeng/api';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, filter, map, switchMap, take } from 'rxjs/operators';
import { JWTTokenService } from '../auth/jwttoken.service';
import { LocalStorageService } from '../auth/storage/local-storage.service';
import { UserRestService } from '../rest/user/user.rest.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor{

  isRefreshing: boolean = false;
  refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  constructor(private jwtService: JWTTokenService,
    private userRestService: UserRestService,
    private messageService: MessageService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(this.setAuthHeader(req)).pipe(
      catchError((error) => {
        if (error instanceof HttpErrorResponse && error.status === 401) {
         return this.handle401Error(req, next);
        } else {
          return throwError(error);
        }
      })
    );
  }

  handle401Error(req: HttpRequest<any>, next: HttpHandler) {
    this.refreshTokenSubject.next(null);

    const token = this.jwtService.getRefreshToken();

    if (token) {
      this.userRestService.refreshToken().subscribe(
      data => { 
        console.log(data.headers.get('access_token'));
        const newToken = data.headers.get('access_token');
        if(newToken) {
          this.jwtService.saveAccessToken(newToken);
          this.messageService.add({severity:'error', summary: 'Something went wrong, try one more time', detail:''});
        }
    });
  }
    
    
      

    return this.refreshTokenSubject.pipe(
      filter(token => token !== null),
      take(1),
      switchMap((token) => next.handle(this.setAuthHeader(req)))
    );
  }
    

  setAuthHeader(req: HttpRequest<any>): HttpRequest<any> {
    if(req.url === 'http://localhost:8080/api/users/token/refresh') {
      return req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getRefreshToken()}`
        }
      })
    }

    if(this.jwtService.getAccessToken()) {
      return req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getAccessToken()}`
        }
      })
    } else {
      return req;
    }
  }
}
