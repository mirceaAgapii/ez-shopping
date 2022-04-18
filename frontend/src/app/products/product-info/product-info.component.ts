import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Product';

@Component({
  selector: 'app-product-info',
  templateUrl: './product-info.component.html',
  styleUrls: ['./product-info.component.css']
})
export class ProductInfoComponent implements OnInit {

  _product!: Product;
  productName = '';
  imgPathPref = 'assets/img/articles/';
  imgPathSuf = '.jpg';

  constructor() { }

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
