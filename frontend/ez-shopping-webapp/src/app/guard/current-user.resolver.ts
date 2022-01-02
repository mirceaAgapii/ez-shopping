import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { User } from '../Model/User';
import { LocalStorageService } from '../services/interceptor/storage/local-storage.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';

@Injectable({
  providedIn: 'root'
})
export class CurrentUserResolver implements Resolve<User> {
 
  constructor(private userRestService: UserRestService,
    protected userService: UserService,
    private localStorageService: LocalStorageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
    const currentUser = this.userService.getCurrentUser();
    if (currentUser) {
      return currentUser;
    } else {
      return this.userRestService.getUserById(this.localStorageService.get("userId"));
    }
  }
}
