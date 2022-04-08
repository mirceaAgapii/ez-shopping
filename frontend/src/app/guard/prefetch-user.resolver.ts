import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Product } from '../Model/Article';
import { User } from '../Model/User';
import { ProductRestService } from '../services/rest/product/product-rest.service';
import { UserRestService } from '../services/rest/user/user.rest.service';

@Injectable({
  providedIn: 'root'
})
export class PrefetchUserResolver implements Resolve<User[]> {

  constructor(private userRestService: UserRestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User[]> | Promise<User[]> | User[] {
    return this.userRestService.getAllUsers();
  }
}
