import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from '../Model/User';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.css']
})
export class UserAccountComponent implements OnInit {

  oldPassword!: string;
  newPassword!: string;
  reNewPassword!: string;
  passwordsMatch = false;
  testUser = new User();
  user!: User;



  constructor(private localStorageService: LocalStorageService,
    private authorizationService: AuthorizationService,
    private router: Router,
    private route: ActivatedRoute,
    private userRestService: UserRestService,
    private messageService: MessageService) { }

  ngOnInit(): void {
    this.passwordsMatch = false;
    this.user = this.route.snapshot.data['user'];
    this.testUser = Object.assign({},this.user);
  }

  logOff() {
    this.localStorageService.remove('access_token');
    this.localStorageService.remove('refresh_token');
    this.localStorageService.remove('userId');
    this.authorizationService.isAuthenticated();
    this.router.navigate(['/login']);
  }

  private checkPasswords(): boolean {
    if(this.oldPassword && this.newPassword && this.reNewPassword) {
      if(this.newPassword === this.reNewPassword) {
        return true;
      }
    }
    return false;
  }

  checkDataChanged(): boolean {
    if (this.user.username !== this.testUser.username
      || this.user.password !== this.testUser.password
      || this.user.email !== this.testUser.email) {
      return true;
    }
    return false;
  }

  updateUserData() {
    if (this.checkPasswords()) {
      this.user.password = this.newPassword;
    }
    if (this.checkDataChanged()) {
      this.userRestService.updateUser(this.user).subscribe(
        data => {
          this.clearData();
          this.testUser = this.user;
          this.messageService.add({severity: 'success', summary: "Data successfully updated", detail: ''});
        },
        error => {
          this.messageService.add({severity: 'error', summary: "Error during updating data", detail: ''});
        }
      );
    } else {
      this.messageService.add({severity: 'warn', summary: "User data hasn't been changed", detail: ''});
    }
  }

  onKeyUp() {
    this.checkPasswords();
  }

  private clearData() {
    this.newPassword = this.oldPassword = this.reNewPassword = '';
  }
}
