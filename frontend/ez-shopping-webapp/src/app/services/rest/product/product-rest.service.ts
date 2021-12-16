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
}
