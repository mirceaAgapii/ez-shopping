import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Product';
import { ProductService } from 'src/app/services/product/product.service';
import { Output, EventEmitter } from '@angular/core';
import { MessageService } from 'primeng/api';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { WebSocketMessage } from 'src/app/Model/WebSocketMessage';
import { ProductRestService } from 'src/app/services/rest/product/product-rest.service';
import { UserService } from 'src/app/services/user/user.service';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit, OnDestroy {

  defaultProduct: Product = new Product();
  statuses!: any[];
  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS01'/*this.userService.currentUser.username*/);

  @Output()
  submitted = new EventEmitter();
  articleDialog: boolean = true;

  constructor(private productService: ProductRestService,
    private messageService: MessageService,
    private userService: UserService) { }

  ngOnInit(): void {
    this.statuses = [
      {label: 'INSTOCK', value: 'instock'},
      {label: 'LOWSTOCK', value: 'lowstock'},
      {label: 'OUTOFSTOCK', value: 'outofstock'}
    ];
    this.connectWS();
  }

  ngOnDestroy(): void {
    this.closeWebSocketSession();
  }

  save(){
    if(this.checkProduct()) {
      let product = new Product();
      product.category = this.defaultProduct.category;
      product.description = this.defaultProduct.description;
      product.status = this.defaultProduct.status;
      product.name = this.defaultProduct.name;
      product.price = this.defaultProduct.price;
      product.quantity = this.defaultProduct.quantity;
      product.barcode = this.defaultProduct.barcode;
      product.rfId = this.defaultProduct.rfId;
      this.productService.saveProduct(product);
      this.submitted.emit();
      this.defaultProduct = new Product();
    }
  }

  cancel(){
    this.submitted.emit();
  }

  checkProduct() {
    if (!(this.defaultProduct.name && this.defaultProduct.price && this.defaultProduct.quantity && this.defaultProduct.rfId)) {
      this.messageService.add({severity:'error', summary: 'Please fill all fields', detail:''});
      return false;
    }
    return true;
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        console.log("Response: " + message.productId);
        this.defaultProduct.rfId = message.productId;
      },
      error => {
        console.error(error);
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }
}
