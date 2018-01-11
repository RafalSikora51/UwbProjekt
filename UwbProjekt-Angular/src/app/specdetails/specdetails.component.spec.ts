import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecdetailsComponent } from './specdetails.component';

describe('SpecdetailsComponent', () => {
  let component: SpecdetailsComponent;
  let fixture: ComponentFixture<SpecdetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecdetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
