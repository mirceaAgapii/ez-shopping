import { Component, OnInit } from '@angular/core';

import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog'
import { User } from '../Model/User';
import { UserRestService } from '../services/user.rest.service';
import { Subscription } from 'rxjs';
import { UserService } from '../services/user.service';
import { LocalStorageService } from '../services/local-storage.service';
import { AuthorizationService } from '../services/auth/authorization.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  disableButton = true;
  username!: string;
  password!: string;


  constructor(private router: Router,
    private restService: UserRestService,
    private userService: UserService,
    private authorizationService: AuthorizationService) {
     }

  showSpinner = false;

  ngOnInit() {
    if(this.authorizationService.isAuthenticated()) {
      console.log('user already logged in');
      this.router.navigate(['']);
    }

  }

  ngOnDestroy() {
  }


  login() : void {
    if(this.username && this.password) {
      this.restService.login(this.username, this.password);
      this.showSpinner = true;
    } else {
      alert('Please enter your Username and Password')
    }

  }

  register() {
    this.router.navigate(['/registration']);
  }
  
  autoLogin() {
    this.restService.login('Mircea', '12345');
    this.showSpinner = true;
  }

}