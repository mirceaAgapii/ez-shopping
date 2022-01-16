import { Component, OnDestroy, OnInit } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Product } from '../Model/Article';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { ProductRestService } from '../services/rest/product/product-rest.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit, OnDestroy {

  barcode!: string;
  rfId!: string;
  placeholder: string = 'Enter a barcode or scan the RFID';

  product!: Product;

  readyToSubmit = false;

  socket: WebSocketSubject<WebSocketMessage> = webSocket('ws://localhost:8080/web-socket/' + 'WS01'/*this.userService.currentUser.username*/);

  constructor(private productRestService: ProductRestService,
    private userService: UserService) { }

  ngOnInit() {
    this.connectWS();
    
  }

  ngOnDestroy() {
    this.closeWebSocketSession();
  }

  searchProduct() {
    if(this.barcode || this.rfId) {
 
      this.productRestService.getProduct(this.barcode, this.rfId)?.subscribe(
        data => {
          this.product = data;
          console.log(data.name);
        },
        error => console.log(error)
      )
    }
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        console.log("Response: " + message.payload);
        this.rfId = message.payload;
        this.barcode = '';
        this.placeholder = 'RFID:' + this.rfId;
        this.readyToSubmit = true;
      },
      error => {
        console.error(error);
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

}
