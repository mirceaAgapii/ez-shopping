import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { LocalStorageService } from '../../auth/storage/local-storage.service';

import { ProductRestService } from './product-rest.service';

describe('ProductRestService', () => {
  let service: ProductRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LocalStorageService],
      imports: [HttpClientModule]
    });
    service = TestBed.inject(ProductRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
