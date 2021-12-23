import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TreeNode } from 'primeng/api';
import { Product } from '../Model/Article';
import { ProductService } from '../services/product/product.service';
import { ProductRestService } from '../services/rest/product/product-rest.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {

  addNewProduct: boolean = false;

  products: Product[] = [];

  first = 0;

  rows = 10;

  constructor(private productRestService: ProductRestService,
    private productService: ProductService,
    private route: ActivatedRoute) { }

  ngOnInit() {
      this.products = this.productService.getProducts();

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
