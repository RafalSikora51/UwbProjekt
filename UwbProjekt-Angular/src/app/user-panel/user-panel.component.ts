import { Component, OnInit } from '@angular/core';
import { UserPanelService } from '../user-panel/user-panel.service';
import { AdminPanelComponent } from '../admin-panel/admin-panel.component';
import { AdminPanelService } from '../admin-panel/admin-panel.service';
import { Doctor } from '../shared/model/doctor';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { Input } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Spec } from '../shared/model/spec';
import { User } from '../shared/model/user';
import { Appointments } from '../shared/model/appointments';
import { Apphour } from '../shared/model/apphour';
import { MedicalHistory } from '../shared/model/medicalhistory';
@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css'],
  providers: [AdminPanelService, AdminPanelComponent]
})

export class UserPanelComponent implements OnInit {

  showDoctors: boolean;
  showMedicalHistory: boolean;
  showAddAppointments: boolean;
  showAppointments: boolean;
  doctors: Doctor[];
  appHours: Apphour[];
  users: User[];
  appointments: Appointments[];
  email: string;
  userId: number;
  medicalHistories: MedicalHistory[];
  constructor(private userPanelService: UserPanelService,
    private route: ActivatedRoute,
    private router: Router, ) { }


  ngOnInit() {
    this.showMedicalHistory = false;
    this.showAddAppointments = false;
    this.showAppointments = false;
    this.showDoctors = false;
    this.getDoctors();
    this.getEmailFromLoggedUser();
    this.getUserIdByEmail();
  }

  getEmailFromLoggedUser() {
    this.email = JSON.parse(localStorage.getItem('currentUser')).email;
  }

  getDoctors(): void {
    this.userPanelService.getDoctors().subscribe(
      doctors => {
        this.doctors = doctors;

      },
      error => {
        console.log(error);
      }
    )
  }

  getUserIdByEmail(): void {
    this.userPanelService.getUserIdByEmail(this.email).subscribe(
      userId => {
        this.userId = userId;

      },
      error => {
        console.log(error);
      }
    )
  }

  getAllAppointmentsForId(): void {
    
    this.userPanelService.getAllAppointmentsForId(this.userId).subscribe(
      appointments => {
        this.appointments = appointments;
        console.table(this.appointments);
      },
      error => {
        console.log(error);
      }
    );
  }

  getAllHours(): void {
    this.userPanelService.getAllHours().subscribe(
      appHours => {
        this.appHours = appHours;
      },
      error => {
        console.log(error);
      }
    )
  }


  showAppointmentsEnable() {

    if (this.showAppointments == false) {
      this.showAppointments = true;
      this.getAllAppointmentsForId();
      this.getAllHours();
      console.log("dziala");
    }
    else {
      this.showAppointments = false;
    }


  }
  showDoctorsEnable() {
    if (this.showDoctors == false)
      this.showDoctors = true;

    else {
      this.showDoctors = false;
    }
  }

  showMedicalHistoryEnable() {
    if (this.showMedicalHistory == false) {
      this.showMedicalHistory = true;
    } else {
      this.showMedicalHistory = false;
    }


  }

}
