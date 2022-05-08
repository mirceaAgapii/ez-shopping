import { TestBed } from '@angular/core/testing';

import { PrefetchPromoProductsResolver } from './prefetch-promo-products.resolver';

describe('PrefetchPromoProductsResolver', () => {
  let resolver: PrefetchPromoProductsResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PrefetchPromoProductsResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
