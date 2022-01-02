import { TestBed } from '@angular/core/testing';

import { PrefetchUserResolver } from './prefetch-user.resolver';

describe('LoadUserResolver', () => {
  let resolver: PrefetchUserResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(PrefetchUserResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
