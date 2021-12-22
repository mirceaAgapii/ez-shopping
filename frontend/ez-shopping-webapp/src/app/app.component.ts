import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from './services/auth/authorization.service';
import { LocalStorageService } from './services/interceptor/storage/local-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ez-shopping-webapp';
  isUserLoggedIn!: boolean;
  constructor(private authorizationService: AuthorizationService,
    private router: Router,
    private localStorageService: LocalStorageService) {}

  ngOnInit(): void {
    this.authorizationService.isUserAuthenticated.subscribe(
      data => {
        this.isUserLoggedIn = data;
        console.log('in app component. userLoggedIn=' + this.isUserLoggedIn);
      }
    );
  }

  navigateToMain() {
    this.router.navigate(['']);
  }


  logOff() {
    console.log('logoff - clicked');
    this.localStorageService.remove('access_token');
    this.localStorageService.remove('refresh_token');
    this.authorizationService.isAuthenticated();
    this.isUserLoggedIn = false;
    this.router.navigate(['/login']);
  }

  toProducts() {
    console.log('clicked on Products');
    this.router.navigate(['/products']);
  }

  toAccount(){
    this.router.navigate(['/admin']);
  }
}
