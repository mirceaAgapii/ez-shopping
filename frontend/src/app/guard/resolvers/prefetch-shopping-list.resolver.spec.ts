import { TestBed } from '@angular/core/testing';

import { PrefetchShoppingListResolver } from './prefetch-shopping-list.resolver';

describe('PrefetchShoppingListResolver', () => {
  let resolver: PrefetchShoppingListResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PrefetchShoppingListResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
