import {AfterViewInit, Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from './services/auth/authorization.service';
import { JWTTokenService } from './services/auth/jwttoken.service';
import {LocalStorageService} from "./services/auth/storage/local-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ez-shopping-webapp';
  isUserLoggedIn = false;
  isUserAdmin = false;
  isUserStation = false;
  isReceiving = false;
  isCheckout = false;

  showReceiving = true;
  showCheckProduct = false;

  constructor(private authorizationService: AuthorizationService,
              private router: Router,
              private jwtService: JWTTokenService,
              private localStorageService: LocalStorageService) {

  }

  ngOnInit(): void {
    this.authorizationService.isUserAuthenticated.subscribe(
      data => {
        this.isUserLoggedIn = data;
        console.log('in app component. userLoggedIn=' + this.isUserLoggedIn);
        const userRoles = this.jwtService.getUserRoles();
        if(userRoles !== null) {
          this.isCheckout = userRoles.includes('CHECKOUT');
          this.isReceiving = userRoles.includes('RECEIVING');
          this.isUserStation = this.isCheckout || this.isReceiving;
        }
      });
  }


  navigateToMain() {
    this.router.navigate(['']);
  }

  toProducts() {
    console.log('clicked on Products');
    this.router.navigate(['/products']);
  }

  toAccount(){
    this.router.navigate(['/account']);
  }

  toAdmin() {
    this.router.navigate(['/admin/dashboard']);
  }

  checkout() {
    this.router.navigate(['/checkout']);
  }

  toBasket() {
    this.router.navigate(['/basket']);
  }

  toShoppingList() {
    this.router.navigate(['/list']);
  }

  logOff() {
    this.localStorageService.remove('access_token');
    this.localStorageService.remove('refresh_token');
    this.localStorageService.remove('userId');
    this.authorizationService.isAuthenticated();
    this.isUserAdmin = false;
    this.isUserStation = false;
    this.router.navigate(['/login']);
  }

  showReceivingComp() {
    this.showReceiving = true;
    this.showCheckProduct = false;
  }

  showCheckProdComp() {
    this.showReceiving = false;
    this.showCheckProduct = true;
  }
}
