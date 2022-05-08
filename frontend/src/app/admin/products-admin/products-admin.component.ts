import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Product } from 'src/app/Model/Product';
import { ProductService } from 'src/app/services/product/product.service';
import { ProductRestService } from 'src/app/services/rest/product/product-rest.service';

@Component({
  selector: 'app-products-admin',
  templateUrl: './products-admin.component.html',
  styleUrls: ['./products-admin.component.css']
})
export class ProductsAdminComponent implements OnInit {

  addNewProduct: boolean = false;

  productToUpdate!:Product;

  products: Product[] = [];

  first = 0;

  rows = 10;

  constructor(private productRestService: ProductRestService,
              private productService: ProductService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.products = this.route.snapshot.data['products'];
  }

  updateProd(product: Product) {
    this.productService.productToUpdate = product;
    this.productService.fromAdmin = true;
    this.router.navigate(['/receiving']);
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
    this.productService.fromAdmin = true;
    this.router.navigate(['/receiving']);
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
