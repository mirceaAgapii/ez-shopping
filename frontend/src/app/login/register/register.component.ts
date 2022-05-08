import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { User } from 'src/app/Model/User';
import { AuthorizationService } from 'src/app/services/auth/authorization.service';
import { UserRestService } from 'src/app/services/rest/user/user.rest.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  showSpinner = false;
  username!: string;
  password!: string;
  re_password!: string;
  email!: string;
  invalid_input: boolean = false;


  constructor(private userRestService: UserRestService,
    private router: Router,
    private authorizationService: AuthorizationService,
    private messageService: MessageService) { }

  ngOnInit(): void {

  }



register() {
    if (!this.username) {
      this.messageService.add({severity:'warn', summary: 'Please provide an username', detail:''});
      console.log('no username');
      return;
    }

    if(this.email && this.email.includes('@') && this.email.includes('.')) {
      console.log('valid email');
    } else {
      this.messageService.add({severity:'warn', summary: 'Email you entered is invalid', detail:''});
      console.log('invalid email');
      return;
    }

    if(this.password && this.re_password && this.password === this.re_password) {
      this.invalid_input = false;
    } else {
      console.log('invalid password');
      this.messageService.add({severity:'warn', summary: 'Passwords don\'t match', detail:''});
      this.invalid_input = true;
      return;

    }

    let user = new User();
    user.username = this.username;
    user.password = this.password;
    user.email = this.email;

    this.userRestService.register(user).subscribe({
      next: data => {
        console.log('success');
        if(!this.authorizationService.isAuthenticated()) {
          this.messageService.add({severity:'success', summary: 'User registered successfully', detail:''});
          this.router.navigate(['/login']);
        }
      },
      error: error => {
        var errorMessage = error.error;
        console.log(error);
        this.messageService.add({severity:'error', summary: errorMessage, detail:''});
      }
    });

  }

  backToLogin() {
    this.router.navigate(['/login']);
  }
}
