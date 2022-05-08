import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Product';
import {ProductRestService} from "../../services/rest/product/product-rest.service";
import {ShoppingListItem} from "../../Model/ShoppingListItem";
import {LocalStorageService} from "../../services/auth/storage/local-storage.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.css']
})
export class ProductInfoComponent implements OnInit {

  _product!: Product;
  productName = '';

  constructor(private productRestService: ProductRestService,
              private storage: LocalStorageService,
              private messageService: MessageService) { }

  @Input() set product(product: Product) {
    this._product = product;
    this.loadUserDetails();
  }

  loadUserDetails() {
    if(this._product && this._product.name) {
      this.productName = this._product.name;
    }
  }

  ngOnInit(): void {
  }

  addToShoppingList(product: Product) {
    let listItem = new ShoppingListItem();
    listItem.productName = product.name;
    listItem.active = !product.done;
    listItem.userId = this.storage.get('userId');
    this.productRestService.saveShoppingListItem(listItem).subscribe(
      data => {
        console.log(product.name + ' successful added to list');
        this.messageService.add({
          severity: 'success',
          summary: `Product ${product.name} added to list`,
          detail: ''
        });
      },
      error => {
        console.log(error.error);
        this.messageService.add({
          severity: 'warn',
          summary: `Product ${product.name} is already in the list`,
          detail: ''
        });
      }
    )
  }
}
