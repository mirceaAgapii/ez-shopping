import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../Model/User';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  loadData: Subscription = new Subscription;
  users: Array<User> = new Array<User>();
  selectedUsers: Array<User> = new Array<User>();
  user: User = new User();
  showSpinner: boolean;

  constructor(private userSrvice: UserService,
    private router: Router,
    private restService: UserRestService,
    private userService: UserService,
    private localStorageService: LocalStorageService) { 
      console.log('in constructor');
      this.showSpinner = false;
    }

  ngOnInit(): void {
    console.log('User logged in: ' + this.userSrvice.getUserLoggedIn());
   
  }

  getAllUsers() {
    this.loadData =  this.restService.getAllUsers().subscribe(data => this.users = data);
  }

}
