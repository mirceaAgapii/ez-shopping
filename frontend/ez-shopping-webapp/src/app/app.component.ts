import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from './services/auth/authorization.service';
import { JWTTokenService } from './services/auth/jwttoken.service';
import { LocalStorageService } from './services/auth/storage/local-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ez-shopping-webapp';
  isUserLoggedIn = false;
  isUserAdmin = false;
  

  constructor(private authorizationService: AuthorizationService,
    private router: Router,
    private jwtService: JWTTokenService) {}

  ngOnInit(): void {
    this.authorizationService.isUserAuthenticated.subscribe(
      data => {
        this.isUserLoggedIn = data;
        console.log('in app component. userLoggedIn=' + this.isUserLoggedIn);
      }
    );

    const userRoles = this.jwtService.getUserRoles();
    if(userRoles !== null) {
      this.isUserAdmin = userRoles.includes('ADMINISTRATOR');
    }
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
}
