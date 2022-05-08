import { Component, OnDestroy, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from '../Model/User';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';
import {Product} from "../Model/Product";
import {JWTTokenService} from "../services/auth/jwttoken.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  isUserAdmin = false;

  loadData: Subscription = new Subscription;
  users: Array<User> = new Array<User>();
  user: User = new User();
  showSpinner: boolean;
  products: Product[] = [];
  logo = 'EZ-Shopping';

  constructor(private userSrvice: UserService,
              private restService: UserRestService,
              private router:Router,
              private route: ActivatedRoute,
              private jwtService: JWTTokenService) {
      this.showSpinner = false;
    }

  ngOnInit(): void {
    console.log('User logged in: ' + this.userSrvice.getUserLoggedIn());
    this.products = this.route.snapshot.data['products'];
    const userRoles = this.jwtService.getUserRoles();
    if(userRoles !== null) {
      this.isUserAdmin = userRoles.includes('ADMINISTRATOR');
    }
  }

  getAllUsers() {
    this.loadData =  this.restService.getAllUsers().subscribe(data => this.users = data);
  }

  openStation() {
    this.router.navigate(['/station']);
  }

  openReceiving() {
    this.router.navigate(['/receiving']);
  }
}
