import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Appointments } from '../../shared/model/appointments';
import { Apphour } from '../../shared/model/apphour';
import { DoctorPanelComponent } from '../doctor-panel.component';
import { DoctorPanelService } from '../doctor-panel.service'
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ToastrService } from 'ngx-toastr';

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
  showAppointmentForm: boolean;
  model: any = {};
  AppointmentID: number;
  appProcess: any;
  submitted = false;
  error: String = '';
  constructor(
    private doctorPancelService: DoctorPanelService,
    private doctorPanelComponent: DoctorPanelComponent,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.showAppointmentDetails = false;
    this.showAppointmentForm = false;
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
  editAppointmentProcess() {
    this.AppointmentID = this.model.id;
    this.appProcess = this.model.appointmentProcess;
    this.doctorPancelService.editAppointmentProcess(this.AppointmentID, this.appProcess)
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Edycja przebiegła pomyślnie!');
        } else {
          this.error = 'Nieprawidłowe dane podczas edycji!';
        }
      },
      () => {
        this.error = 'Błąd połączenia z serwerem.'
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
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

  showAppointmentProcessForm(id) {
    this.model.id = id;
    console.log("Jestem w ShowAppointmentProcessForm, id modelu : " + this.model.id);
    if (this.showAppointmentForm == false) {
      this.showAppointmentForm = true;
    } else {
      this.showAppointmentForm = false;
    }
  }
  showAppointmentDetailsEnable(id: number) {
    this.model.id = id;
    if (this.showAppointmentDetails == false) {
      this.showAppointmentDetails = true;
    } else {
      this.showAppointmentDetails = false;
    }

  }

  onSubmit() {

    console.log("Jestem w onSubmit " + this.model.id + "AppointmentProcess: " + this.model.appointmentProcess);
    this.editAppointmentProcess();


  }

}
