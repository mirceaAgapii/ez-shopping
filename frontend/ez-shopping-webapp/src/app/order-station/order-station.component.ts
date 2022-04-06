import { Component, OnDestroy, OnInit } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Product } from '../Model/Article';
import { Orderline } from '../Model/Orderline';
import { WebSocketMessage } from '../Model/WebSocketMessage';

@Component({
  selector: 'app-order-station',
  templateUrl: './order-station.component.html',
  styleUrls: ['./order-station.component.css']
})
export class OrderStationComponent implements OnInit, OnDestroy {

  hideCart = true;
  socket: WebSocketSubject<WebSocketMessage> = webSocket('ws://localhost:8080/web-socket/' + 'WS02'/*this.userService.currentUser.username*/);
  orderlines: Array<Orderline> = new Array();
  orderId: string = '';

  constructor() { }

  ngOnInit(): void {
    this.connectWS();
  }

  ngOnDestroy(): void {
    this.closeWebSocketSession();
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        
        if(this.orderId === '' || this.orderId === message.orderId) {
          if(message.orderLines) {
            for(let olDTO of message.orderLines) {
              this.updateOrderLines(olDTO);
            }
          } else if (message.orderLineDTO) {
            this.updateOrderLines(message.orderLineDTO);
          }
          this.orderId = message.orderId;
          this.hideCart = false;
        }
        
      },
      error => {
        console.error(error);
      });
  }

  updateOrderLines(olDTO: Orderline) {
    var index = this.orderlines.findIndex(ol => ol.productId === olDTO.productId);
    if(index === -1) {
      var ol = new Orderline();
      ol.productId = olDTO.productId;
      ol.productName = olDTO.productName;
      ol.olQty = olDTO.olQty;
      this.orderlines.push(ol);
    } else {
      this.orderlines[index].olQty = olDTO.olQty;
    }
  }

  finishOrder() {
    var message = new WebSocketMessage();
    message.finished = true;
    message.orderId = this.orderId;
    this.socket.next(message);
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

}
