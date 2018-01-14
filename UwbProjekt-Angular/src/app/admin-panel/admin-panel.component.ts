import { Component, OnInit } from '@angular/core';
import { AdminPanelService } from '../admin-panel/admin-panel.service';
import { Doctor } from './DoctorConstructor';
import { DoctorsService } from '../doctors/doctors.service';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { FormArray } from '@angular/forms/src/model';

import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  id: number;
  doctor: Doctor;
  doctorForm: FormGroup;
  model: any = {};
  specModel: any = {};
  loading = false;
  checkbox: boolean;
  returnUrl: string;

  constructor(private route: ActivatedRoute, private router: Router, private adminPanelService: AdminPanelService, private toastr: ToastrService) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';


    // this.doctorForm = new FormGroup({
    //     countryId: new FormControl('', Validators.required),
    //     email: new FormControl('', [
    //     Validators.required,
    //     Validators.pattern("[^ @]*@[^ @]*")
    //   ]),
    //   lastName: new FormControl('', Validators.required),
    //   firstName: new FormControl('', Validators.required),
    //   admin: new FormControl('', Validators.required),
    // });

  }

  // onSubmit(){
  //   if(this.doctorForm.valid){
  //     let doctor:Doctor = new Doctor(
  //       this.doctorForm.controls['countryId'].value,
  //       this.doctorForm.controls['email'].value,
  //       this.doctorForm.controls['lastName'].value,
  //       this.doctorForm.controls['firstName'].value,
  //       this.doctorForm.controls['admin'].value);

  //      // this.adminPanelService.createDoctor(doctor).subscribe();
  //   }
  // }


  createDoctor() {
    this.loading = true;
    this.adminPanelService.createDoctor(this.model, this.specModel.specName)
      .subscribe(
      data => {
        this.toastr.success('Lekarz dodany pomyślnie!');
        this.loading = false;
        this.router.navigate(['/adminpanel']);
      },
      error => {
        this.toastr.error('Nie udało się dodać lekarza!');
        this.loading = false;
      });
  }

  clicked() {
    console.log('click')
  }

  changed(evt) {
    this.checkbox = evt.target.checked;
    console.log(evt.target.checked)
  }



  onSubmit() {
    if (this.checkbox) {
      this.model.admin = true;
      console.log('Tworze z prawami admina.')
      this.createDoctor();
    }
    else {
      this.model.admin = false;
      console.log('Tworze bez praw admina.')
      this.createDoctor();
    }
  }



}
