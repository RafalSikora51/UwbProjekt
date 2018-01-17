import { Component, OnInit } from '@angular/core';
import { DoctorDetailsService } from './doctor-details.service';
import { Doctor } from './doctor';

import { Input } from '@angular/core';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css'],
  providers: [DoctorDetailsService]
})
export class DoctorDetailsComponent implements OnInit {


  constructor(private doctorDetailsService: DoctorDetailsService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.getDoctorById();
  }

  doctor: Doctor;

  getDoctorById(): void {

    const id = +this.route.snapshot.paramMap.get('id');
    this.doctorDetailsService.getDoctorById(id).subscribe(
      doctor => {
        this.doctor = doctor;
        console.table(this.doctor);
      },
      error => {
        console.log(error);
      }
    );
  }

}
