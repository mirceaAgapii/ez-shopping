import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';
import {environment} from 'src/environments/environment';
import {WebSocketMessage} from '../Model/WebSocketMessage';
import {LocalStorageService} from '../services/auth/storage/local-storage.service';
import {OrderRestService} from '../services/rest/order/order-rest.service';
import {UserService} from '../services/user/user.service';
import {Orderline} from "../Model/Orderline";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.css']
})
export class BasketComponent implements OnInit, OnDestroy {

  productsCount = 0;
  totalPrice = 0;
  orderlines: Orderline[] = [];
  currentOrderId!: string;

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS02/' + this.storage.get("orderUserId"));

  constructor(private router: Router,
              private userService: UserService,
              private storage: LocalStorageService,
              private orderRestService: OrderRestService) {
  }

  ngOnInit(): void {
    this.updateOrderDetails();
    this.connectWS();
  }

  ngOnDestroy(): void {
    this.closeWebSocketSession();
  }

  removeProduct(id: string) {
    this.orderlines.forEach(ol => {
      if (ol.productId == id) {
        let orderLine = this.orderlines[this.orderlines.indexOf(ol)];
        this.orderRestService.removeOrderLine(orderLine.orderLineId).subscribe(
          next => {
            this.orderlines.splice(this.orderlines.indexOf(ol), 1);
            this.updateOrderDetails();
          },
          error => {
            console.log(error);
          }
        )
      }
    })
  }

  navigateToMain() {
    this.router.navigate(["/station"]);
  }

  updateOrderDetails() {
    this.productsCount = this.orderlines.length;
    this.totalPrice = 0;
    this.orderlines.forEach(p => {
      this.totalPrice += p.price;
    })
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        if (message.orderLines.length > 0) {
          message.orderLines.forEach(ol => {
            if (this.orderlines.filter(o => o.productId === ol.productId).length === 0) {
              let orderLine = new Orderline();
              orderLine = ol;
              this.orderlines.push(orderLine);
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
    this.orderlines = [];
    this.currentOrderId = '';
    this.updateOrderDetails();
    this.router.navigate(['/station']);
  }

}
