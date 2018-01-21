import { Component, OnInit } from '@angular/core';
import { Spec } from '../shared/model/spec';
import { MedicalHistoryService } from './medical-history.service';
import { MedicalHistory } from '../shared/model/medicalhistory';
import { UserPanelComponent } from '../user-panel/user-panel.component';
import { UserPanelService } from '../user-panel/user-panel.service';
import { Apphour } from '../shared/model/apphour';
import { Appointments } from '../shared/model/appointments';
@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit {
  public specs: Spec[];
  medicalHistories: MedicalHistory[];
  medicalHistoryId: number;
  appointmentDetailsId: number;
  appointments: Appointments[];
  showAppointmentDetails: boolean;
  showMedicalHistoryForUser: boolean;
  showAllAppointmentsForUserByMedicalSpec: boolean;
  appHours: Apphour[];
  constructor(private medicalHistoryService: MedicalHistoryService,
    private userPanelComponent: UserPanelComponent) { }


  ngOnInit() {

    this.showMedicalHistoryForUser = false;
    this.showAllAppointmentsForUserByMedicalSpec = false;
    this.showAppointmentDetails = false;
    this.getSpecs();
    this.findMedicalHistoryForUser();
    this.getAllHours();


  }

  getSpecs(): void {
    this.medicalHistoryService.getSpecs().subscribe(
      specs => {
        this.specs = specs;
        console.table(this.specs);
      },
      error => {
        console.log(error);
      }
    );
  }

  findAllAppointmentsForUserByMedicalSpec(medicalHistorySpecId): void {
    const id = this.userPanelComponent.userId;

    this.medicalHistoryId = medicalHistorySpecId;
    console.log("Id wybranego Spec: " + medicalHistorySpecId);
    this.medicalHistoryService.findAllAppointmentsForUserByMedicalSpec(id, this.medicalHistoryId).subscribe(
      appointments => {
        this.appointments = appointments;
        console.table(this.appointments);
      },
      error => {
        console.log(error);
      }
    )
  }

  findMedicalHistoryForUser(): void {
    const id = this.userPanelComponent.userId;
    this.medicalHistoryService.findMedicalHistoryForUser(id).subscribe(
      medicalHistories => {
        this.medicalHistories = medicalHistories;
        console.table(this.medicalHistories);
      },
      error => {
        console.log(error);
      }
    );
  }

  showMedicalHistoryEnable() {
    if (this.showMedicalHistoryForUser == false) {
      this.showMedicalHistoryForUser = true;
    }
    else {
      this.showMedicalHistoryForUser = false;
    }
  }

  getAllHours(): void {
    this.medicalHistoryService.getAllHours().subscribe(
      appHours => {
        this.appHours = appHours;
      },
      error => {
        console.log(error);
      }
    )
  }
 

  // Dwie funkcje w jednej - do wyświetlania wizyt
  showshowAllAppointmentsClick(medicalHistorySpecId) {
    this.showshowAllAppointmentsForUserByMedicalSpecEnable();
    this.findAllAppointmentsForUserByMedicalSpec(medicalHistorySpecId);
  }
  showshowAllAppointmentsForUserByMedicalSpecEnable() {
    if (this.showAllAppointmentsForUserByMedicalSpec == false) {
      this.showAllAppointmentsForUserByMedicalSpec = true;

    } else {
      this.showAllAppointmentsForUserByMedicalSpec = false;
    }
  }
  showAppointmentDetailsEnable() {
    if (this.showAppointmentDetails == false) {
      this.showAppointmentDetails = true;
    } else {
      this.showAppointmentDetails = false;
    }

  }

}