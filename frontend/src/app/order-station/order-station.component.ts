import { Component, OnDestroy, OnInit } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { environment } from 'src/environments/environment';
import {Router} from "@angular/router";
import {LocalStorageService} from "../services/auth/storage/local-storage.service";

@Component({
  selector: 'app-order-station',
  templateUrl: './order-station.component.html',
  styleUrls: ['./order-station.component.css']
})
export class OrderStationComponent implements OnInit, OnDestroy {

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS02/none');

  constructor(private router: Router,
              private storage: LocalStorageService) { }

  ngOnInit(): void {
    this.connectWS();
  }

  ngOnDestroy(): void {
    this.closeWebSocketSession();
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        if (message.userId !== null) {
          console.log(`Received response with user id [${message.userId}]`);
          this.storage.set('orderUserId', message.userId);
          this.router.navigate(['/basket']);
        }
      },
      error => {
        console.error(error);
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

}
