import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoresAdminComponent } from './stores-admin.component';

describe('StoresAdminComponent', () => {
  let component: StoresAdminComponent;
  let fixture: ComponentFixture<StoresAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StoresAdminComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StoresAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
