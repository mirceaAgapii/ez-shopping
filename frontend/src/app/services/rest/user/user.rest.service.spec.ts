import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AuthorizationService } from '../../auth/authorization.service';
import { JWTTokenService } from '../../auth/jwttoken.service';
import { LocalStorageService } from '../../auth/storage/local-storage.service';

import { UserRestService } from './user.rest.service';

describe('RestService', () => {
  let service: UserRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JWTTokenService, AuthorizationService, LocalStorageService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    service = TestBed.inject(UserRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
