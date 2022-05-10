import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'src/app/Model/Product';
import { environment } from 'src/environments/environment';
import {LocalStorageService} from "../../auth/storage/local-storage.service";
import {ShoppingListItem} from "../../../Model/ShoppingListItem";

@Injectable({
  providedIn: 'root'
})
export class ProductRestService {

  constructor(private http: HttpClient,
              private storage: LocalStorageService) { }

  getAllProducts() {
    return this.http.get<Product[]>(environment.restUrl + environment.api + '/products' );
  }

  getAllProductNames() {
    return this.http.get<String[]>(environment.restUrl + environment.api + '/products/names' );
  }

  getPromoProducts() {
    return this.http.get<Product[]>(environment.restUrl + environment.api + '/products/promo' );
  }

  getProduct(barcode: string, rfId: string) {
    if(barcode) {
      return this.http.get<Product>(environment.restUrl + environment.api + '/products/product?barcode=' + barcode);
    } else if(rfId) {
      return this.http.get<Product>(environment.restUrl + environment.api + '/products/product?rfId=' + rfId);
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
    return this.http.post<any>(environment.restUrl + environment.api + '/products/save', body);
  }

  saveImage(file: any, rfid: string) {

    const fd = new FormData();
    fd.append('image', file, file.name);
    return this.http.post<any>(environment.restUrl + environment.api + '/products/image/' + rfid,fd);
  }

  getShoppingListForCurrentUser() {
    return this.http.get<ShoppingListItem[]>(environment.restUrl + environment.api + '/products/shoppinglist/' + this.storage.get('userId'));
  }

  saveShoppingList(productNames: string[]) {
    let userId = this.storage.get('userId');
    let body: ShoppingListItem[] = [];
    for(let i = 0; i < productNames.length; i++) {
      let sl = new ShoppingListItem();
      sl.userId = userId;
      sl.productName = productNames[i];
      body.push(sl);
    }

    this.http.post<any>(environment.restUrl + environment.api + '/products/shoppinglist', body).subscribe({
      next: data => {
        console.log('successful update');
      },
      error: error => {
        console.log(error);
      }
    })
  }

  saveShoppingListItem(item: ShoppingListItem) {
    let userId = this.storage.get('userId');
    let body: ShoppingListItem = new ShoppingListItem();
    body.userId = userId;
    body.productName = item.productName;
    body.active = item.active;

    return this.http.post<ShoppingListItem>(environment.restUrl + environment.api + '/products/shoppingitem', body);
  }

  deleteShoppingListItem(itemId: string) {
    return this.http.delete<any>(environment.restUrl + environment.api + '/products/shoppinglist/' + itemId).subscribe(
      data => console.log('success')
    );
  }

  updateShoppingListItemStatus(item: ShoppingListItem) {
    let body = {}
    return this.http.patch(environment.restUrl + environment.api + '/products/shoppinglist/' + item.id + '?isActive=' + item.active, body).subscribe(
      data => console.log('success')
    );
  }

  updateProduct(product: Product) {
    return this.http.patch(environment.restUrl + environment.api + '/products/update', product);
  }

}
