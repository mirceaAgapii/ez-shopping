import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Article';

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
