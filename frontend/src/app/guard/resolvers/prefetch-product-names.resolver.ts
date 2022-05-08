import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {ProductRestService} from "../../services/rest/product/product-rest.service";

@Injectable({
  providedIn: 'root'
})
export class PrefetchProductNamesResolver implements Resolve<String[]> {
  constructor(private productRestService: ProductRestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<String[]> | Promise<String[]> | String[] {
    return this.productRestService.getAllProductNames();
  }
}
