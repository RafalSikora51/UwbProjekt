import { Component, OnInit } from '@angular/core';
import { DoctorDetailsService } from './doctor-details.service';
import { Doctor } from './doctor';
@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css'],
  providers: [DoctorDetailsService]
})
export class DoctorDetailsComponent implements OnInit {

  constructor(private doctorDetailsService: DoctorDetailsService) { }

  doctors: Doctor[];

  ngOnInit() {
    this.getDoctors();
  }
  
  getDoctors(): void {
    this.doctorDetailsService.getDoctors().subscribe(
      doctors => {
        this.doctors = doctors;
        console.table(this.doctors);
      },
      error => {
        console.log(error);
      }

    )
  }
}
