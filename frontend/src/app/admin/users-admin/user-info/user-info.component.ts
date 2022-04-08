import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { UserRestService } from 'src/app/services/rest/user/user.rest.service';
import {User} from "../../../Model/User";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit, OnDestroy {

  private _user!: User;

  usernameValid = true;
  emailValid = true;
  userUpdated = false;

  @Input() set user(user: User) {
    this._user = user;
    this.loadUserDetails();
  }

  username = '';
  email = '';
  role = '';

  roles!: String[];

  @Output()
  submitted = new EventEmitter();


  constructor(private userRestService: UserRestService) { }

  ngOnInit(): void {
    this.roles = ['CLIENT', 'ADMINISTRATOR', 'PRICE_CHECK', 'SUPERVISOR'];    
  }

  ngOnDestroy(): void {
    this.submitted.unsubscribe();
  }

  close() {
    this.submitted.emit();
  }

  updateUserDetails() {
    if(this.username !== this._user.username) {
      this._user.username = this.username;
      this.userUpdated = true;
    }
    if(this.email !== this._user.email) {
      this._user.email = this.email;
      this.userUpdated = true;
    }
    if(this.role !== this._user.role) {
      this._user.role = this.role;
      this.userUpdated = true;
    }
    if(this.userUpdated) {
      this.userRestService.updateUser(this._user);
      this.userUpdated = false;
    }
    this.close();
  }

  loadUserDetails() {
    if(this._user && this._user.username && this._user.email && this._user.role) {
      this.username = this._user.username;
      this.email = this._user.email;
      this.role = this._user.role;
    }
  }
}
