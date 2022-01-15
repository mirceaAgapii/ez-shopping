import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
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
  isUserAdmin = false;

  constructor(private router: Router,
    private localStorageService: LocalStorageService,
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
    const userRoles = this.jwtService.getUserRoles();
    if(userRoles !== null) {
      this.isUserAdmin = userRoles.includes('ADMINISTRATOR');
    }
    
  }

  navigateToMain() {
    this.router.navigate(['']);
  }


  toProducts() {
    this.router.navigate(['/products']);
  }

  toAccount(){
    this.router.navigate(['/account']);
  }

  toAdmin() {
    this.router.navigate(['/admin/dashboard']);
  }

}
