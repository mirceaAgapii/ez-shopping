import { TestBed } from '@angular/core/testing';

import { LoadDataResolver } from './load-data.resolver';

describe('LoadDataResolver', () => {
  let resolver: LoadDataResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(LoadDataResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
