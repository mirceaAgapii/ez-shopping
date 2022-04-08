import { Location } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';

import { AdminComponent } from './admin.component';
import { DashboardComponent } from './dashboard/dashboard.component';

fdescribe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;
  let router: Router;
  let location: Location;
  let isAuthorized = false; 

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminComponent ],
      imports: [
        HttpClientModule, 
        RouterTestingModule.withRoutes([
          {path: 'admin', component: AdminComponent, canActivate: [MockAuthotizationGuard]}
        ])
      ]
    })
    .compileComponents();
    
    router = TestBed.get(Router);
    location = TestBed.get(Location);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //Test 5
  it('should open dashboard on click', () => {
    router.navigate(['/dashboard']).then( () =>
      expect(location.path()).toBe('/dashboard')
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
