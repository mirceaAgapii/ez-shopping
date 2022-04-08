import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { JWTTokenService } from './jwttoken.service';
import { LocalStorageService } from './storage/local-storage.service';

describe('JWTTokenService', () => {
  let service: JWTTokenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LocalStorageService, JWTTokenService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    service = TestBed.inject(JWTTokenService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
