import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';

import { AdminPermisionsGuard } from './admin-permisions.guard';

describe('AdminPermisionsGuard', () => {
  let guard: AdminPermisionsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JWTTokenService, LocalStorageService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    guard = TestBed.inject(AdminPermisionsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
