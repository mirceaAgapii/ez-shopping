import { Component, OnInit } from '@angular/core';

import {Router} from '@angular/router';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { User } from '../Model/User';
import { UserService } from '../services/user/user.service';
import { HttpResponse } from '@angular/common/http';
import { MessageService } from 'primeng/api';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username!: string;
  password!: string;


  constructor(private router: Router,
    private restService: UserRestService,
    private authorizationService: AuthorizationService,
    private jwtService: JWTTokenService,
    private messageService: MessageService,
    private localStorageService: LocalStorageService) {
    }

  showSpinner = false;

  ngOnInit() {
    if(this.authorizationService.isAuthenticated()) {
      this.router.navigate(['']);
    }
  }

  addSingle() {
    this.messageService.add({severity:'error', summary:`User ${this.username} wasn't found`, detail:''});
  }

  onKeydown(event: { key: string; }) {
    console.log(event.key);
    if (event.key === "F6") {
      this.autoLogin();
    }
  }


  login() : void {
    if(this.username && this.password) {
      this.restService.login(this.username, this.password).subscribe(
        data => {
          this.saveTokens(data);
          this.router.navigate(['']);
          const userId = data.headers.get("userId");
          if(userId !== null) {
            this.localStorageService.set('userId', userId);
          }
        },
        error => {
          if(error.error.status === 461) {
            this.showSpinner = false;
            this.messageService.add({severity:'error', summary:`User ${this.username} wasn't found`, detail:''});
            this.username = '';
            this.password = '';
          }
        }
      );
      this.showSpinner = true;
    } else if(!this.username) {
      this.messageService.add({severity:'warn', summary: 'Please provide your username', detail:''});
    } else {
      this.messageService.add({severity:'warn', summary: 'Please provide your password', detail:''});
    }

  }

  register() {
    this.router.navigate(['/registration']);
  }

  autoLogin() {
    this.username = 'Mircea';
    this.password = '12345';
    this.login();
    this.showSpinner = true;
  }

  private saveTokens(data: HttpResponse<Object>) {
    var token: string | null;
    var refresh_token: string | null;
    token = data.headers.get("access_token");
    refresh_token = data.headers.get("refresh_token");
    if(token && refresh_token) {
      this.jwtService.saveAccessToken(token);
      this.jwtService.saveRefreshToken(refresh_token);
    }
  }

}
