import { TestBed } from '@angular/core/testing';

import { AdminPermisionsGuard } from './admin-permisions.guard';

describe('AdminPermisionsGuard', () => {
  let guard: AdminPermisionsGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AdminPermisionsGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
