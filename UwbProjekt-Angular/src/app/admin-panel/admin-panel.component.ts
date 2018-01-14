import { Component, OnInit } from '@angular/core';
import { AdminPanelService } from '../admin-panel/admin-panel.service';
import { Doctor } from './DoctorConstructor';
import { DoctorsService } from '../doctors/doctors.service';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { FormArray } from '@angular/forms/src/model';
@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  id: number;
  doctor: Doctor;
  doctorForm: FormGroup;
  constructor(private route: ActivatedRoute, private adminPanelService: AdminPanelService) { }

  ngOnInit() {
    this.doctorForm = new FormGroup({
        countryId: new FormControl('', Validators.required),
        email: new FormControl('', [
        Validators.required,
        Validators.pattern("[^ @]*@[^ @]*")
      ]),
      lastName: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      admin: new FormControl('', Validators.required),
    });
  }

  onSubmit(){
    if(this.doctorForm.valid){
      let doctor:Doctor = new Doctor(
        this.doctorForm.controls['countryId'].value,
        this.doctorForm.controls['email'].value,
        this.doctorForm.controls['lastName'].value,
        this.doctorForm.controls['firstName'].value,
        this.doctorForm.controls['admin'].value);
        
       // this.adminPanelService.createDoctor(doctor).subscribe();
    }
  }

}
