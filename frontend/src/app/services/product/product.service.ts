import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  products: Product[] = [];


  addProduct(product: Product) {
    this.products.push(product);
  }

  constructor() {

   }
}
