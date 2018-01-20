import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorUsersComponent } from './doctor-users.component';

describe('DoctorUsersComponent', () => {
  let component: DoctorUsersComponent;
  let fixture: ComponentFixture<DoctorUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DoctorUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
