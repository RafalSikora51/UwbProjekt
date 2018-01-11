import { TestBed, inject } from '@angular/core/testing';

import { SpecdetailsService } from './specdetails.service';

describe('SpecdetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpecdetailsService]
    });
  });

  it('should be created', inject([SpecdetailsService], (service: SpecdetailsService) => {
    expect(service).toBeTruthy();
  }));
});
