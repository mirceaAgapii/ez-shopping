import { TestBed } from '@angular/core/testing';

import { PrefetchProductNamesResolver } from './prefetch-product-names.resolver';

describe('PrefetchProductNamesResolver', () => {
  let resolver: PrefetchProductNamesResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PrefetchProductNamesResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
