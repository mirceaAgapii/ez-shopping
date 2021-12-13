import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { JWTTokenService } from '../jwttoken.service';
import { UserRestService } from '../user.rest.service';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  isUserAuthenticated = new BehaviorSubject<boolean>(false);

  constructor(private jwtTokeService: JWTTokenService,
    private userRestService: UserRestService) { }

  public isAuthenticated(): boolean {
    const rawToken = localStorage.getItem('access_token');
    if (rawToken) {

      this.isUserAuthenticated.next(!this.jwtTokeService.isTokenExpired());
      return !this.jwtTokeService.isTokenExpired();
    } else {
      this.isUserAuthenticated.next(false);
      return false;
    }
  }
}
