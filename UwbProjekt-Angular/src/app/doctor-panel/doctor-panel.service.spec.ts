import { TestBed, inject } from '@angular/core/testing';

import { DoctorPanelService } from './doctor-panel.service';

describe('DoctorPanelService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DoctorPanelService]
    });
  });

  it('should be created', inject([DoctorPanelService], (service: DoctorPanelService) => {
    expect(service).toBeTruthy();
  }));
});
