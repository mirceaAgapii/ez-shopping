import { Injectable } from '@angular/core';
import { JwtPayload } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';
import { User } from '../../Model/User';
import { JWTTokenService } from '../auth/jwttoken.service';
import { LocalStorageService } from '../auth/storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!: User;
  currentUserSubject = new BehaviorSubject(new User());
  private userLoggedIn: boolean;

  constructor(private localStorageService: LocalStorageService) {
    this.userLoggedIn = false;
   }

  public getUserLoggedIn(): boolean {
    return this.userLoggedIn;
  }
  public setUserLoggedIn(value: boolean) {
    this.userLoggedIn = value;
  }

  getCurrentUser(): User {
    return this.currentUser;
  }

  setCurrentUser(user: User) {
    this.currentUser = user;
    this.localStorageService.set('userId', user.id);
  }


}
