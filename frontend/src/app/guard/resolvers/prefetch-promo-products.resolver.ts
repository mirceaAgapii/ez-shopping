import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {ProductRestService} from "../../services/rest/product/product-rest.service";
import {Product} from "../../Model/Product";

@Injectable({
  providedIn: 'root'
})
export class PrefetchPromoProductsResolver implements Resolve<Product[]>  {
  constructor(private productRestService: ProductRestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product[]> | Promise<Product[]> | Product[] {
    return this.productRestService.getPromoProducts();
  }
}
