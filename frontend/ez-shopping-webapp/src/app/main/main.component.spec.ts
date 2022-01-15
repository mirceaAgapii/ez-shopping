import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';

import { MainComponent } from './main.component';

describe('MainComponent', () => {
  let component: MainComponent;
  let fixture: ComponentFixture<MainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [UserService, UserRestService,LocalStorageService, JWTTokenService],
      declarations: [ MainComponent ],
      imports: [HttpClientModule, RouterTestingModule]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
