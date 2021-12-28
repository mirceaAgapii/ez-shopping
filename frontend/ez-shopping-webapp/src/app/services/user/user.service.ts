import { Injectable } from '@angular/core';
import { JwtPayload } from 'jwt-decode';
import { BehaviorSubject } from 'rxjs';
import { User } from '../../Model/User';
import { JWTTokenService } from '../auth/jwttoken.service';
import { LocalStorageService } from '../interceptor/storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  currentUser!: User;
  currentUserSubject = new BehaviorSubject(new User());
  private userLoggedIn: boolean;

  constructor(private localStorageService: LocalStorageService,
    private jwtService: JWTTokenService) {
    this.userLoggedIn = false;
   }

  public getUserLoggedIn(): boolean {
    return this.userLoggedIn;
  }
  public setUserLoggedIn(value: boolean) {
    this.userLoggedIn = value;
  }

  getCurrentUser(): User {
    this.currentUser.username = this.jwtService.getUser();
    return this.currentUser;
  }

  setCurrentUser(user: User) {
    this.currentUser = user;
    this.currentUserSubject.next(this.currentUser);
  }


}
