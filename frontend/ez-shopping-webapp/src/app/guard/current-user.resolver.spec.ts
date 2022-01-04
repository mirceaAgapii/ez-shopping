import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { JWTTokenService } from '../services/auth/jwttoken.service';
import { LocalStorageService } from '../services/auth/storage/local-storage.service';
import { UserRestService } from '../services/rest/user/user.rest.service';
import { UserService } from '../services/user/user.service';

import { CurrentUserResolver } from './current-user.resolver';

describe('CurrentUserResolver', () => {
  let resolver: CurrentUserResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UserRestService, UserService, LocalStorageService, JWTTokenService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    resolver = TestBed.inject(CurrentUserResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
