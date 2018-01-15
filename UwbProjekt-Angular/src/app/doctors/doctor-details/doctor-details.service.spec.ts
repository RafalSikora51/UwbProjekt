import { TestBed, inject } from '@angular/core/testing';

import { DoctorDetailsService } from './doctor-details.service';

describe('DoctorDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DoctorDetailsService]
    });
  });

  it('should be created', inject([DoctorDetailsService], (service: DoctorDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
