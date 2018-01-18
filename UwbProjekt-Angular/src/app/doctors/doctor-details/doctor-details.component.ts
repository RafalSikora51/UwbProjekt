import { Component, OnInit, Input } from '@angular/core';
import { Route } from '@angular/compiler/src/core';
import { Router, ActivatedRoute, Params } from '@angular/router';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldControl } from '@angular/material/form-field/';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';
import { DoctorDetailsService } from './doctor-details.service';
import { Doctor } from './doctor';
import { Apphour } from '../../shared/model/apphour';
import { HeaderComponent } from '../../header/header.component';



@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.css'],
  providers: [DoctorDetailsService, { provide: MAT_DATE_LOCALE, useValue: 'pl-PL' }]
})
export class DoctorDetailsComponent implements OnInit {

  doctor: Doctor;
  apphours: Apphour[];
  model: any = {};
  showHours: boolean;
  hourModel: any = {};
  idDoctor: string;
  loggedUser: any;
  email: string;

  constructor(
    private doctorDetailsService: DoctorDetailsService,
    private route: ActivatedRoute,
    private router: Router,
    private adapter: DateAdapter<any>,
    private toastr: ToastrService, ) { }

  ngOnInit() {
    this.getEmailFromLoggedUser();
    this.getDoctorById();
    this.adapter.setLocale('pl');
    this.showHours = false;
    this.idDoctor = this.route.snapshot.paramMap.get('id');
  }

  isUserLoggedIn() { return JSON.parse(localStorage.getItem('currentUser')) };
  isAdminLoggedIn() { return JSON.parse(localStorage.getItem('currentAdmin')) };
  isDoctorLoggedIn() { return JSON.parse(localStorage.getItem('currentDoctor')) };
  isDoctorAdminLoggedIn() { return JSON.parse(localStorage.getItem('currentDoctorAdmin')) };

  getEmailFromLoggedUser() {
    if (this.isUserLoggedIn()) {
      this.email=JSON.parse(localStorage.getItem('currentUser')).email;
    }
    else if (this.isAdminLoggedIn()) {
      this.email=JSON.parse(localStorage.getItem('currentAdmin')).email;
    }
    else if (this.isDoctorLoggedIn()) {
      this.email=JSON.parse(localStorage.getItem('currentDoctor')).email;
    }
    else {
      this.email=JSON.parse(localStorage.getItem('currentDoctorAdmin')).email;
    }
  }


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

  getFreeHoursForDoctorFromGivenDay(date: String): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.doctorDetailsService.getFreeHoursForDoctorFromGivenDay(id, date).subscribe(
      apphours => {
        this.apphours = apphours;
        console.table(this.apphours);
      },
      error => {
        console.log(error);
      }
    );
  }

  createAppointment() {
    this.doctorDetailsService.createAppointment(this.email, this.idDoctor, this.hourModel.id, this.model.abc.toLocaleDateString())
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Wizyta została zarejestrowana pomyślnie!');
          window.location.reload();
        } else {
          this.toastr.error('Nie udało się zarejestrować wizyty!');
          window.location.reload();
        }
      },
      () => {
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }


  showHoursEnable() {
    if (this.model.abc !== undefined) {
      this.showHours = !this.showHours;
      this.getFreeHoursForDoctorFromGivenDay(this.model.abc.toLocaleDateString());
      console.log('pokazuje godziny');
      console.log(this.showHours);
    }
    else {
      this.toastr.error('Zanim zostaną wyświetlone dostępne godziny, wybierz dzień!');
    }
  }



}
