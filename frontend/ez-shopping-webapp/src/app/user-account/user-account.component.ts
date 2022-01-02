import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from '../Model/User';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/interceptor/storage/local-storage.service';
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
  }


  
  logOff() {
    console.log('logoff - clicked');
    this.localStorageService.remove('access_token');
    this.localStorageService.remove('refresh_token');
    this.localStorageService.remove('userId');
    this.authorizationService.isAuthenticated();
    this.router.navigate(['/login']);
  }

  private checkPasswords() {
    if(this.oldPassword && this.newPassword && this.reNewPassword) {
      if(this.newPassword !== this.reNewPassword) {
        this.passwordsMatch = false;
      } else {
        this.passwordsMatch = true;
      }
    }

  }

  updatePassword() {
    this.userRestService.updatePassword(this.oldPassword, 
      this.newPassword, 
      this.localStorageService.get("userId"))
      .subscribe(
        data => {
           this.clearData();
           this.messageService.add({severity:'success', summary: "Password successfuly updated", detail:''});
        },
        error => {
          this.messageService.add({severity:'error', summary: error.error, detail:''});
        }
    );

  }

  onKeyUp() {
    this.checkPasswords();
  }

  private clearData() {
    this.newPassword = this.oldPassword = this.reNewPassword = '';

  }
}
