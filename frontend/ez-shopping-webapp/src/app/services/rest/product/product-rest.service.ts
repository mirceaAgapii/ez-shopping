import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from 'src/app/Model/Article';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProductRestService {

  constructor(private http: HttpClient) { }

  getAllProducts() {
    return this.http.get<Product[]>(environment.restUrl + '/products' );
  }

  getProduct(barcode: string, rfId: string) {
    if(barcode) {
      return this.http.get<Product>(environment.restUrl + '/products/product?barcode=' + barcode);
    } else if(rfId) {
      return this.http.get<Product>(environment.restUrl + '/products/product?rfId=' + rfId);
    }
    return null;
  }

  saveProduct(product: Product) {
    const body = {
      name : product.name,
      description : product.description,
      price : product.price,
      quantity : product.quantity,
      barcode : product.barcode,
      status : product.status,
      category : product.category,
      rfId: product.rfId
    }

    this.http.post<any>(environment.restUrl + '/products/save', body).subscribe({
      next: data => {
        console.log('successful update');
      },
      error: error => {
        console.log(error);
      }
    })
  }
}
