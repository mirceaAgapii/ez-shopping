import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { PrefetchDashboardResolver } from './prefetch-dashboard.resolver';

describe('PrefetchDashboardResolver', () => {
  let resolver: PrefetchDashboardResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule]
    });
    resolver = TestBed.inject(PrefetchDashboardResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
