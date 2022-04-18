import { Component, OnDestroy, OnInit, Output } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from 'src/environments/environment';
import { Product } from '../Model/Product';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { ProductRestService } from '../services/rest/product/product-rest.service';
import { UserService } from '../services/user/user.service';
import {LocalStorageService} from "../services/auth/storage/local-storage.service";

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

  readyToSubmit = true;

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS03/' + this.storage.get('userId'));

  constructor(private productRestService: ProductRestService,
    private storage: LocalStorageService) { }

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
        if (message.payload !== undefined) {
          console.log("Response: " + message.payload);
          this.rfId = message.payload;
          this.barcode = '';
          this.placeholder = 'RFID:' + this.rfId;
          this.searchProduct();
        }
      },
      error => {
        console.error(error);
        this.readyToSubmit = false;
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

}
