import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from 'src/environments/environment';
import { Product } from '../Model/Product';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { OrderRestService } from '../services/rest/order/order-rest.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit, OnDestroy {

  productsCount = 0;
  totalPrice = 0;
  productsList: Product[] = [];
  imgPathPref: string = '../../assets/img/articles/';
  imgPathSuf: string = '.jpg';
  currentOrderId!: string;

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS02/' + this.storage.get("orderUserId"));
  //socket: WebSocketSubject<WebSocketMessage> = webSocket('ws://localhost:8090/web-socket/' + 'WS02/' + this.storage.get("userId"));

  constructor(private router: Router,
    private userService: UserService,
    private storage: LocalStorageService) { }

  ngOnInit(): void {
    this.updateOrderDetails();
    this.connectWS();
  }

  ngOnDestroy(): void {
  this.closeWebSocketSession();
  }

  removeProduct(id: string) {
    this.productsList.forEach(p => {
      if (p.id == id) {
        this.productsList.splice(this.productsList.indexOf(p), 1);
      }
    })
    this.updateOrderDetails();
  }

  navigateToMain() {
    this.router.navigate(["/station"]);
  }

  updateOrderDetails() {
    this.productsCount = this.productsList.length;
    this.totalPrice = 0;
    this.productsList.forEach(p => {
      this.totalPrice += p.price;
    })
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        if (message.orderLines.length > 0) {
          message.orderLines.forEach(ol => {
            if (this.productsList.filter(p => p.id === ol.productId).length === 0) {
              var product = new Product();
              product.id = ol.productId;
              product.name = ol.productName;
              product.price = ol.price;
              product.description = ol.productDescr;
              this.productsList.push(product);
            } else {
              console.log(`${ol.productId} is already in list`);
            }
          })
        }
        this.updateOrderDetails();
        this.currentOrderId = message.orderId;
      },
      error => {
        console.error(error);
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

  finishOrder() {
    const message = new WebSocketMessage();
    message.finished = true;
    message.orderId = this.currentOrderId;
    this.socket.next(message);
    this.productsList = [];
    this.currentOrderId = '';
    this.updateOrderDetails();
  }

}
