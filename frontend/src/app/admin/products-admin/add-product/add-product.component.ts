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
import {ProductService} from "../../../services/product/product.service";
import {JWTTokenService} from "../../../services/auth/jwttoken.service";

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit, OnDestroy {

  isUserStation = false;

  defaultProduct: Product = new Product();
  statuses!: any[];
  socket: WebSocketSubject<WebSocketMessage> = webSocket(environment.wsUrl + '/web-socket/' + 'WS01/none');

  selectedFile!: ImageSnippet;

  defaultUploadImage = 'assets/img/up.png';

  @Input()
  fromAdmin = false;

  @Output()
  submitted = new EventEmitter();

  @Input() set product(product: Product) {
    if(product) {
      this.defaultProduct = product;
    }
  }

  constructor(private productRestService: ProductRestService,
              private productService: ProductService,
              private messageService: MessageService,
              private router: Router,
              private jwtService: JWTTokenService) { }

  ngOnInit(): void {
    this.statuses = [
      {label: 'IN-STOCK', value: 'instock'},
      {label: 'LOW-STOCK', value: 'lowstock'},
      {label: 'OUT-OF-STOCK', value: 'outofstock'},
      {label: 'PROMO', value: 'promo'}
    ];
    this.connectWS();

    this.fromAdmin = this.productService.fromAdmin;
    if(this.productService.productToUpdate) {
      this.defaultProduct = this.productService.productToUpdate;
      this.productService.productToUpdate = null;
    }
    const userRoles = this.jwtService.getUserRoles();
    if(userRoles !== null) {
      this.isUserStation = userRoles.includes('CHECKOUT') || userRoles.includes('RECEIVING');
    }
  }

  ngOnDestroy(): void {
    this.closeWebSocketSession();
  }

  save(){
    if(this.checkProduct()) {
      if(this.defaultProduct.id) {
        this.productRestService.updateProduct(this.defaultProduct).subscribe(
          data => {
            if(this.selectedFile) {
              this.productRestService.saveImage(this.selectedFile.file, this.defaultProduct.id);
            }
          }
        )
      } else {
        this.productRestService.saveProduct(this.defaultProduct).subscribe(
          data => {
            console.log('success ' + data);
            console.log('save image');
            if (this.selectedFile) {
              this.productRestService.saveImage(this.selectedFile.file, data.id).subscribe(
                next => {
                  console.log('image saved');
                  this.defaultProduct = new Product();
                  const preview = document.getElementById('img-preview');
                  // @ts-ignore
                  preview.src = this.defaultUploadImage;
                }
              );
            }
          }
        );
      }

      this.cancel();
    }
  }

  private backToAdmin() {
    this.router.navigate(['/admin/products']);
    this.productService.fromAdmin = false;
  }

  cancel(){
    if(this.fromAdmin) {
      this.backToAdmin();
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
