import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/interceptor/storage/local-storage.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  items!: MenuItem[];
  currentUser!: string | null;
  isUserLoggedIn!: boolean;

  constructor(private router: Router,
    private localStorageService: LocalStorageService,
    private authorizationService: AuthorizationService,
    private userService: UserService, 
    private jwtService: JWTTokenService) { }

  ngOnInit() {
    this.isUserLoggedIn = this.localStorageService.get('access_token') ? true : false;
    if(!this.isUserLoggedIn) {
      this.router.navigate(['/login']);
    }
    this.userService.currentUserSubject.subscribe(
      data => {
        this.currentUser = data.username;
        console.log('new username[' + this.currentUser + ']');
      }
    )
    this.currentUser = this.jwtService.getUser();
  }

  
    

  navigateToMain() {
    this.router.navigate(['']);
  }


  logOff() {
    console.log('logoff - clicked');
    this.localStorageService.remove('access_token');
    this.localStorageService.remove('refresh_token');
    this.authorizationService.isAuthenticated();
    this.isUserLoggedIn = false;
    this.router.navigate(['/login']);
  }

  toProducts() {
    console.log('clicked on Products');
    this.router.navigate(['/products']);
  }

  toAccount(){
    console.log('toAccount clicked');
  }

  toAdmin() {
    this.router.navigate(['/admin']);
  }

}
