import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from 'src/environments/environment.prod';
import { Product } from '../Model/Product';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { OrderRestService } from '../services/rest/order/order-rest.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
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

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS02/' + this.storage.get("userId"));
  //socket: WebSocketSubject<WebSocketMessage> = webSocket('ws://localhost:8090/web-socket/' + 'WS02/' + this.storage.get("userId"));

  constructor(private router: Router,
    private userService: UserService,
    private storage: LocalStorageService,
    private orderRestService: OrderRestService) { }

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
    this.router.navigate([""]);
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
        console.log("Response: " + message.productId);
        var product = new Product();
        product.price = message.price;
        product.name = message.productName;
        product.description = message.productDescr;
        product.id = message.productId;
        this.productsList.push(product);
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
    this.orderRestService.finishOrder(this.currentOrderId).subscribe({
      next: data => {
        this.productsList = [];
        this.currentOrderId = '';
        this.updateOrderDetails();
      }
    })
  }

}
