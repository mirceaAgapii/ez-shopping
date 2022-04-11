import { TestBed } from '@angular/core/testing';

import { OrderRestService } from './order-rest.service';

describe('OrderRestService', () => {
  let service: OrderRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrderRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
