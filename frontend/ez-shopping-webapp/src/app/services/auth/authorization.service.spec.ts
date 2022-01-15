import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { AuthorizationService } from './authorization.service';
import { JWTTokenService } from './jwttoken.service';
import { LocalStorageService } from './storage/local-storage.service';

describe('AuthorizationService', () => {
  let service: AuthorizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JWTTokenService, LocalStorageService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    service = TestBed.inject(AuthorizationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
