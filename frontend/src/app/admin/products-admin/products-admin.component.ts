import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Product } from 'src/app/Model/Product';
import { WebSocketMessage } from 'src/app/Model/WebSocketMessage';
import { ProductService } from 'src/app/services/product/product.service';
import { ProductRestService } from 'src/app/services/rest/product/product-rest.service';

@Component({
  selector: 'app-products-admin',
  templateUrl: './products-admin.component.html',
  styleUrls: ['./products-admin.component.css']
})
export class ProductsAdminComponent implements OnInit {

  addNewProduct: boolean = false;

  products: Product[] = [];

  first = 0;

  rows = 10;

  constructor(private productRestService: ProductRestService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.products = this.route.snapshot.data['products'];
  }

  next() {
      this.first = this.first + this.rows;
  }

  prev() {
      this.first = this.first - this.rows;
  }

  reset() {
      this.first = 0;
  }

  isLastPage(): boolean {
      return this.products ? this.first === (this.products.length - this.rows): true;
  }

  isFirstPage(): boolean {
      return this.products ? this.first === 0 : true;
  }

  enableAddProductComp() {
    this.addNewProduct = !this.addNewProduct;
  }

  closeComp() {
    this.addNewProduct = false;
  }

  loadProducts() {
    this.productRestService.getAllProducts().subscribe(
      data => this.products = data
    );
  }


}
