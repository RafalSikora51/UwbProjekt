import { Component, OnInit } from '@angular/core';
import { SpecsStatsService } from './specs-stats.service';
import { Spec } from '../shared/model/spec';

@Component({
  selector: 'app-specs-stats',
  templateUrl: './specs-stats.component.html',
  styleUrls: ['./specs-stats.component.css']
})
export class SpecsStatsComponent implements OnInit {

  specs: Spec[];
  specsStats: any[];
  currentPage = 1;

  constructor(private specsStatsService: SpecsStatsService) { }

  ngOnInit() {
    this.getSpecs();
    this.getSpecsStats();
  }

  getSpecs(): void {
    this.specsStatsService.getSpecs().subscribe(
      specs => {
        this.specs = specs;
        console.table(this.specs);
      },
      error => {
        console.log(error);
      }
    );
  }

  getSpecsStats(): void {
    this.specsStatsService.getSpecsStats().subscribe(
      specs => {
        this.specsStats = specs;
        console.table(this.specsStats);
      },
      error => {
        console.log(error);
      }
    );
  }


}
