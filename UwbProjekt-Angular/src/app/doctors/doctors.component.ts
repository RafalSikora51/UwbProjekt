import { Component, OnInit } from '@angular/core';
import { DoctorsService } from './doctors.service';
import { Doctor } from '../shared/model/doctor';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css'],
  providers: [DoctorsService]
})
export class DoctorsComponent implements OnInit {

  doctors: Doctor[];

  constructor(private doctorsService: DoctorsService) { }

  ngOnInit() {
    this.getDoctors();
  }

  getDoctors(): void{
    this.doctorsService.getDoctors().subscribe(
      doctors => {
        this.doctors=doctors;
        console.table(this.doctors);
      },
      error => {
        console.log(error);
      }
    );
  }

}
