import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  products: Product[] = [];

  productToUpdate!: Product | null;
  //used in admin products
  fromAdmin = false;


  addProduct(product: Product) {
    this.products.push(product);
  }

  constructor() {

   }
}
