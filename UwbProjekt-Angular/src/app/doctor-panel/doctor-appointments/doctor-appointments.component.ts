import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointments } from '../../shared/model/appointments';
import { Apphour } from '../../shared/model/apphour';
import { DoctorPanelComponent } from '../doctor-panel.component';
import { DoctorPanelService } from '../doctor-panel.service'



@Component({
  selector: 'app-doctor-appointments',
  templateUrl: './doctor-appointments.component.html',
  styleUrls: ['./doctor-appointments.component.css']
})
export class DoctorAppointmentsComponent implements OnInit {

  apps: Appointments[];
  appHours: Apphour[];
  currentPage = 1;
  showAppointmentDetails: boolean;
  model: any = {};


  constructor(
    private doctorPancelService: DoctorPanelService,
    private doctorPanelComponent: DoctorPanelComponent,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.showAppointmentDetails = false;
    this.getAllAppointmentsForDoctor();
    this.getAllHours();
  }

  getAllAppointmentsForDoctor(): void {
    let id;
    if (this.route.snapshot.paramMap.get('id')) {
      id = this.route.snapshot.paramMap.get('id');
    } else {
      id = this.doctorPanelComponent.id;
    }
    this.doctorPancelService.getAllAppointmentsForDoctor(id).subscribe(
      apps => {
        this.apps = apps;
        console.table(this.apps);
      },
      error => {
        console.log(error);
      }
    );
  }

  getAllHours(): void {
    this.doctorPancelService.getAllHours().subscribe(
      appHours => {
        this.appHours = appHours;
      },
      error => {
        console.log(error);
      }
    )
  }

  showAppointmentDetailsEnable(id: number) {
    this.model.id = id;
    console.log(this.model.id + ' AAAAAAAAAAAAAAAAaa')
    if (this.showAppointmentDetails == false) {
      this.showAppointmentDetails = true;
    } else {
      this.showAppointmentDetails = false;
    }

  }


}
