import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Product } from 'src/app/Model/Product';
import { Output, EventEmitter } from '@angular/core';
import { MessageService } from 'primeng/api';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { WebSocketMessage } from 'src/app/Model/WebSocketMessage';
import { ProductRestService } from 'src/app/services/rest/product/product-rest.service';
import { environment } from 'src/environments/environment';
import {ImageSnippet} from "../../../Model/ImageSnippet";
import {Route, Router} from "@angular/router";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit, OnDestroy {

  defaultProduct: Product = new Product();
  statuses!: any[];
  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS01/none');

  selectedFile!: ImageSnippet;

  @Input()
  fromAdmin = false;

  @Output()
  submitted = new EventEmitter();

  constructor(private productService: ProductRestService,
              private messageService: MessageService,
              private router: Router) { }

  ngOnInit(): void {
    this.statuses = [
      {label: 'IN-STOCK', value: 'instock'},
      {label: 'LOW-STOCK', value: 'lowstock'},
      {label: 'OUT-OF-STOCK', value: 'outofstock'},
      {label: 'PROMO', value: 'promo'}
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
      product.barcode = this.defaultProduct.barcode;
      product.rfId = this.defaultProduct.rfId;
      this.productService.saveProduct(product);
      this.submitted.emit();
      this.defaultProduct = new Product();
    }
  }

  cancel(){
    if(this.fromAdmin) {
      this.submitted.emit();
    } else {
      this.router.navigate(['/']);
    }
  }

  checkProduct() {
    if (!(this.defaultProduct.name && this.defaultProduct.price && this.defaultProduct.rfId)) {
      this.messageService.add({severity:'error', summary: 'Please fill all fields', detail:''});
      return false;
    }
    return true;
  }

  connectWS() {
    this.socket.subscribe(
      message => {
        console.log("Response: " + message.payload);
        this.defaultProduct.rfId = message.payload;
      },
      error => {
        console.error(error);
      });
  }

  closeWebSocketSession() {
    this.socket.complete();
  }

  showPreview(input: any) {
    const file: File = input.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {

      this.selectedFile = new ImageSnippet(event.target.result, file);
      if (this.selectedFile.file.type.match('image/*')) {
        if (this.selectedFile.file.size <= 250000) {
          const preview = document.getElementById('img-preview');
          // @ts-ignore
          preview.src = this.selectedFile.src;
          // @ts-ignore
          preview.style.display = "block";
          // @ts-ignore
          preview.hidden = false;
        } else {
          this.messageService.add({severity: 'error', summary: 'Image size is too large', detail: ''});
        }
      } else {
        this.messageService.add({severity: 'error', summary: 'Please upload an image', detail: ''});
      }


    });

    reader.readAsDataURL(file);

  }
}
