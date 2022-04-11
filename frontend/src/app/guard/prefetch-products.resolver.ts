import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import { Product } from '../Model/Product';
import { ProductRestService } from '../services/rest/product/product-rest.service';

@Injectable({
  providedIn: 'root'
})
export class PrefetchProductsResolver implements Resolve<Product[]> {

  constructor(private productRestService: ProductRestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product[]> | Promise<Product[]> | Product[] {
    return this.productRestService.getAllProducts();
  }
}
