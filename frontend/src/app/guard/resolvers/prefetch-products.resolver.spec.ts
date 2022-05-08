import { HttpClient, HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ProductRestService } from '../../services/rest/product/product-rest.service';

import { PrefetchProductsResolver } from './prefetch-products.resolver';

describe('PrefetchProductsResolver', () => {
  let resolver: PrefetchProductsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProductRestService],
      imports: [HttpClientModule]
    });
    resolver = TestBed.inject(PrefetchProductsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
