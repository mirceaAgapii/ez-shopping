import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {ProductRestService} from "../../services/rest/product/product-rest.service";
import {ShoppingListItem} from "../../Model/ShoppingListItem";

@Injectable({
  providedIn: 'root'
})
export class PrefetchShoppingListResolver implements Resolve<ShoppingListItem[]> {
  constructor(private productRestService: ProductRestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ShoppingListItem[]> | Promise<ShoppingListItem[]> | ShoppingListItem[] {
    return this.productRestService.getShoppingListForCurrentUser();
  }
}
