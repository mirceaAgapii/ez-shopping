import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { LocalStorageService } from '../auth/storage/local-storage.service';

import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LocalStorageService],
      imports: [HttpClientModule]
    });
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
