import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/Model/User';
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
    private router: Router) { }

  ngOnInit(): void {

  }


  register() {

    if(this.password && this.re_password && this.password === this.re_password) {
      this.invalid_input = false;
    } else {
      console.log('invalid password');
      alert('Passwords don\'t match');
      this.invalid_input = true;
    }

    if(this.email && this.email.includes('@') && this.email.includes('.')) {
      console.log('valid email');
    } else {
      alert('Email you entered is invalid')
      console.log('invalid email');
    }

    let user = new User();
    user.username = this.username;
    user.password = this.password;
    user.email = this.email;

    this.userRestService.register(user);

  }

  backToLogin() {
    this.router.navigate(['/login']);
  }
}
