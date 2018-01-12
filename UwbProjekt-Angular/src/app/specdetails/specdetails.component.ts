import { Component, OnInit } from '@angular/core';
import { Doctor } from '../shared/model/doctor';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { SpecdetailsService } from '../specdetails/specdetails.service';

@Component({
  selector: 'app-specdetails',
  templateUrl: './specdetails.component.html',
  styleUrls: ['./specdetails.component.css']
})
export class SpecdetailsComponent implements OnInit {
 
  doctors: Doctor[];

  constructor(private specdetailsService: SpecdetailsService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.getDoctorsBySpecId();
  }

  getDoctorsBySpecId(): void{
    const id = +this.route.snapshot.paramMap.get('id');
    this.specdetailsService.getDoctorsBySpecId(id).subscribe(
      doctors => {
        this.doctors=doctors;
        console.table(this.doctors);
      },
      error =>{
      console.log(error);
      }
    );
  }

  private log(message: string) {
    console.log(message);
  }

}
