import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecsStatsComponent } from './specs-stats.component';

describe('SpecsStatsComponent', () => {
  let component: SpecsStatsComponent;
  let fixture: ComponentFixture<SpecsStatsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecsStatsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecsStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
