import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MessageService } from 'primeng/api';
import { JWTTokenService } from '../auth/jwttoken.service';
import { LocalStorageService } from '../auth/storage/local-storage.service';
import { UserRestService } from '../rest/user/user.rest.service';

import { TokenInterceptorService } from './token-interceptor.service';

describe('TokenInterceptorService', () => {
  let service: TokenInterceptorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JWTTokenService, UserRestService, MessageService, LocalStorageService],
      imports: [HttpClientModule, RouterTestingModule]
    });
    service = TestBed.inject(TokenInterceptorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
