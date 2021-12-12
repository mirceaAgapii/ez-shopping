import { AfterContentInit, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AuthorizationService } from './services/auth/authorization.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'ez-shopping-webapp';
  userLoggedIn!: boolean;
  constructor(private authorizationService: AuthorizationService,
    private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cd.detectChanges();
    this.authorizationService.isUserAuthenticated.subscribe(
      data => {
        this.userLoggedIn = data;
        console.log('in app component. userLoggedIn=' + this.userLoggedIn);
      }
    );
  }


}
