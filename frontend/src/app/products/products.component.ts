import {Component, HostListener, OnDestroy, OnInit, Output} from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { environment } from 'src/environments/environment';
import { Product } from '../Model/Product';
import { WebSocketMessage } from '../Model/WebSocketMessage';
import { ProductRestService } from '../services/rest/product/product-rest.service';
import { UserService } from '../services/user/user.service';
import {LocalStorageService} from "../services/auth/storage/local-storage.service";
import {JWTTokenService} from "../services/auth/jwttoken.service";

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit, OnDestroy {

  isClient = false;
  barcode!: string;
  rfId!: string;
  placeholderStation: string = 'Enter a barcode or scan the RFID';
  placeholderClient: string = 'Enter a barcode';

  product!: Product;

  readyToSubmit = true;

  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS01/' + this.storage.get('userId'));

  constructor(private productRestService: ProductRestService,
              private storage: LocalStorageService,
              private jwtService: JWTTokenService) { }

  @HostListener('window:resize', ['$event'])
  onResize() {
    if(window.innerWidth < 767) {

      document.getElementById('search-row')!.classList.remove('row');
    } else {
      document.getElementById('search-row')!.classList.add('row');
    }
  }

  ngOnInit() {
    this.connectWS();
    if(window.innerWidth < 767) {
      document.getElementById('search-row')!.classList.remove('row');
    }
    let userRoles = this.jwtService.getUserRoles();
    if(userRoles !== null) {
      this.isClient = userRoles.includes('CLIENT');
    }
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
          this.barcode = '';
          this.placeholderStation = '';
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
          this.placeholderStation = 'RFID:' + this.rfId;
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

  onKeydown(event: { key: string; }) {
    console.log(event.key);
    if (event.key === "Enter") {
      this.searchProduct();
    }
  }


}
