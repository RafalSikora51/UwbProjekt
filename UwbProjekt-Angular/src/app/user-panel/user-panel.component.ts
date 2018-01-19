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

@Component({
  selector: 'app-user-panel',
  templateUrl: './user-panel.component.html',
  styleUrls: ['./user-panel.component.css'],
  providers: [AdminPanelService, AdminPanelComponent]
})

export class UserPanelComponent implements OnInit {

  showDoctors: boolean;
  showAddAppointments: boolean;
  showAppointments: boolean;
  doctors: Doctor[];
  users: User[];
  appointments: Appointments[];
  constructor(private userPanelService: UserPanelService,
    private route: ActivatedRoute,
    private router: Router, ) { }

  ngOnInit() {
    this.showAddAppointments = false;
    this.showAppointments = false;
    this.showDoctors = false;
    this.getDoctors();

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

  getAllAppointmentsForId(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.userPanelService.getAllAppointmentsForId(id).subscribe(
      appointments => {
        this.appointments = appointments;
        console.table(this.appointments);
      },
      error => {
        console.log(error);
      }
    );
  }


  showAppointmentsEnable() {

    if (this.showAppointments == false) {
      this.showAppointments = true;
      this.getAllAppointmentsForId();
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

}
