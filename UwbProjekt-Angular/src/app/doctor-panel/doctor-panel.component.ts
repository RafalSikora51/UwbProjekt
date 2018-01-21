import { Component, OnInit } from '@angular/core';
import { DoctorPanelService } from '../doctor-panel/doctor-panel.service';
@Component({
  selector: 'app-doctor-panel',
  templateUrl: './doctor-panel.component.html',
  styleUrls: ['./doctor-panel.component.css']
})
export class DoctorPanelComponent implements OnInit {

  showUsers: boolean;
  showAppointments: boolean;
  id: number;
  email: string;



  constructor(
    private doctorPanelService: DoctorPanelService
  ) { }

  ngOnInit() {
    this.showUsers = false;
    this.showAppointments = false;
    this.getEmailFromLoggedDoctor();
    this.getDoctorIdByEmail();
  }

  getEmailFromLoggedDoctor() {
    if (localStorage.getItem('currentDoctor')) {
      this.email = JSON.parse(localStorage.getItem('currentDoctor')).email;
    }
    else {
      this.email = JSON.parse(localStorage.getItem('currentDoctorAdmin')).email;
    }
  }

  getDoctorIdByEmail(): void {
    this.doctorPanelService.getDoctorIdByEmail(this.email).subscribe(
      id => {
        this.id = id;
      },
      error => {
        console.log(error);
      }
    )
  }

  showUsersEnable() {
    if (this.showUsers == false) {
      this.showUsers = true;
    } else {
      this.showUsers = false;
    }
  }

  showAppointmentsEnable() {
    if (this.showAppointments == false) {
      this.showAppointments = true;
    } else {
      this.showAppointments = false;
    }
  }





}
