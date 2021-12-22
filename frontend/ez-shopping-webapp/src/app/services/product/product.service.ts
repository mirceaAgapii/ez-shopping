import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Article';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  products: Product[] = [];

  getProducts(): Product[] {
    for(let i = 1; i < 11; i++) {
      let prod = new Product();
      prod.name = 'Product' + i;
      prod.category = 'Products';
      prod.inventoryStatus = 'In Stock';
      prod.price = 50 * i;
      prod.quantity = i + 2;
      this.products.push(prod);
    }
    return this.products;
  }

  addProduct(product: Product) {
    this.products.push(product);
  }

  constructor() {
    
   }
}
