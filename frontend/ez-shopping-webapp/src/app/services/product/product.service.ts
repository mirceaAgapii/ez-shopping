import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Article';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  products: Product[] = [];

  getProducts(): Product[] {
    return this.products;
  }

  addProduct(product: Product) {
    this.products.push(product);
  }

  constructor() { }
}
