import { Component, OnInit } from '@angular/core';
import { SpecsService } from './specs.service';
import { Spec } from '../shared/model/spec';

@Component({
  selector: 'app-specs',
  templateUrl: './specs.component.html',
  styleUrls: ['./specs.component.css'],
  providers: [SpecsService]
})
export class SpecsComponent implements OnInit {

  specs: Spec[];

  constructor(private specService: SpecsService) { }

  ngOnInit() {
    this.getSpecs();
  }

  getSpecs(): void {
    this.specService.getSpecs().subscribe(
      specs => {
        this.specs = specs;
        console.table(this.specs);
      },
      error => {
        console.log(error);
      }
    );

  }

}
