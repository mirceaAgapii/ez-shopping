import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Article';
import { ProductService } from 'src/app/services/product/product.service';
import { Output, EventEmitter } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {

  defaultProduct: Product = new Product();

  statuses!: any[];

  @Output()
  submitted = new EventEmitter();
  articleDialog: boolean = true;

  constructor(private productService: ProductService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.statuses = [
      {label: 'INSTOCK', value: 'instock'},
      {label: 'LOWSTOCK', value: 'lowstock'},
      {label: 'OUTOFSTOCK', value: 'outofstock'}
  ];
  }

  save(){
    if(this.checkProduct()) {
      let product = new Product();
      product.category = this.defaultProduct.category;
      product.description = this.defaultProduct.description;
      product.image = this.defaultProduct.image;
      product.inventoryStatus = this.defaultProduct.inventoryStatus;
      product.name = this.defaultProduct.name;
      product.price = this.defaultProduct.price;
      product.quantity = this.defaultProduct.quantity;
      this.productService.addProduct(product);
      this.submitted.emit();
      this.defaultProduct = new Product();
    }
  }

  cancel(){
    this.submitted.emit();
  }

  checkProduct() {
    if (!(this.defaultProduct.name && this.defaultProduct.price && this.defaultProduct.quantity)) {
      this.messageService.add({severity:'error', summary: 'Please fill all fields', detail:''});
      return false;
    }
    return true;
  }


}
