import { TestBed } from '@angular/core/testing';

import { PrefetchProductsResolver } from './prefetch-products.resolver';

describe('PrefetchProductsResolver', () => {
  let resolver: PrefetchProductsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PrefetchProductsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
