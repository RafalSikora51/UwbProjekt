import { Component, OnInit } from '@angular/core';
import { DoctorsService } from './doctors.service';

import { ToastrService } from 'ngx-toastr';

import { Doctor } from '../shared/model/doctor';
import { Spec } from '../shared/model/spec';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css'],
  providers: [DoctorsService]
})
export class DoctorsComponent implements OnInit {

  doctors: Doctor[];
  specs: Spec[];

  constructor(
    private doctorsService: DoctorsService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.getDoctors();
    this.getSpecs();
  }

  getDoctors(): void {
    this.doctorsService.getDoctors().subscribe(
      doctors => {
        this.doctors = doctors;
        console.table(this.doctors);
      },
      error => {
        console.log(error);
      }
    );
  }

  getSpecs(): void {
    this.doctorsService.getSpecs().subscribe(
      specs => {
        this.specs = specs;
        console.table(this.specs);
      },
      error => {
        console.log(error);
      }
    );
  }

  changeAdminRights(id: number) {
    this.doctorsService.changeAdminRights(id).subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Prawa administratora zmieniono pomyślnie!');
          this.getDoctors();
        } else {
          this.toastr.error('Nie udało się zmienić praw administratora!');
        }
      },
      () => {
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }

  onSubmit(id: number): void {
    this.changeAdminRights(id);
  }



}
