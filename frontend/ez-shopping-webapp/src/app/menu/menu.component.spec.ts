import { Location } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService } from 'primeng/api';
import { Observable } from 'rxjs';
import { AdminComponent } from '../admin/admin.component';
import { AppRoutingModule, routes } from '../app-routing.module';
import { LoginComponent } from '../login/login.component';
import { AuthorizationService } from '../services/auth/authorization.service';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { UserService } from '../services/user/user.service';

import { MenuComponent } from './menu.component';

describe('MenuComponent', () => {
  let component: MenuComponent;
  let fixture: ComponentFixture<MenuComponent>;
  let router: Router;
  let location: Location;
  let isAuthorized = false;



  beforeEach(async(() => {

    TestBed.configureTestingModule({
      providers: [
        LocalStorageService,
        JWTTokenService, 
        UserService, 
        MessageService,
        AuthorizationService,
        MockAuthotizationGuard
      ],
      imports: [RouterTestingModule.withRoutes([
        {path: 'admin', component: AdminComponent, canActivate: [MockAuthotizationGuard]},
        {path: 'login', component: LoginComponent}
      ]), 
      HttpClientModule],
      declarations: [ MenuComponent ]
    })
    .compileComponents();

    router = TestBed.get(Router);
    location = TestBed.get(Location);
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MenuComponent);
    component = fixture.componentInstance;
    fixture.autoDetectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //Test 1
  it('should have last hav-bar element the account', () => {
    expect(document.querySelector('.nav_links li:last-child a')!.innerHTML).toEqual('Account');
  });

  //Test 2
  it('should have admin panel element in nav bar', () => {
    let elems: Array<any> = new Array<any>();
    document.querySelectorAll('a').forEach(a => {
      elems.push(a.innerText);
    });
    expect(elems.includes('Admin Page')).toBeTrue();
  })

  //Test 3
  it('should redirect to login when not authorized', () => {
    router.navigate(['/admin']).catch( () =>
      expect(location.path()).toBe('/login')
    )
  })

  //Test 4
  it('should redirect to admin if is authorized', () => {
    isAuthorized = true;
    router.navigate(['/admin']).then( () =>
      expect(location.path()).toBe('/admin')
    )
  })

  class MockAuthotizationGuard implements CanActivate {

    canActivate(
      route: ActivatedRouteSnapshot, 
      state: RouterStateSnapshot
    ): boolean {
      if (isAuthorized) {
        return true;
      }
      return false;
    }
  }
});
