import { TestBed } from '@angular/core/testing';

import { WorkstationGuard } from './workstation-guard.service';

describe('WorkstationGuard', () => {
  let guard: WorkstationGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(WorkstationGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
