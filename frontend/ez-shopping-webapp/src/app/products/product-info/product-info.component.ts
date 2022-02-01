import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Article';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.css']
})
export class ProductInfoComponent implements OnInit {

  constructor() { }

  private _product!: Product;
  productName = '';

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

}
