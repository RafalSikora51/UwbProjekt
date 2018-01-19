import { TestBed, inject } from '@angular/core/testing';

import { SpecsStatsService } from './specs-stats.service';

describe('SpecsStatsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpecsStatsService]
    });
  });

  it('should be created', inject([SpecsStatsService], (service: SpecsStatsService) => {
    expect(service).toBeTruthy();
  }));
});
