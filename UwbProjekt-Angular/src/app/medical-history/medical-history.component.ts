import { Component, OnInit } from '@angular/core';
import { Spec } from '../shared/model/spec';
import { MedicalHistoryService } from './medical-history.service';
import { SpecsService } from '../specs/specs.service';
import { MedicalHistory } from '../shared/model/medicalhistory';
import { UserPanelComponent } from '../user-panel/user-panel.component';
import { UserPanelService } from '../user-panel/user-panel.service';
import { AdminPanelComponent } from '../admin-panel/admin-panel.component';
import { AdminPanelService } from '../admin-panel/admin-panel.service';
import { Doctor } from '../shared/model/doctor';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { Input } from '@angular/core';
import { User } from '../shared/model/user';
import { Appointments } from '../shared/model/appointments';
import { Apphour } from '../shared/model/apphour';

@Component({
  selector: 'app-medical-history',
  templateUrl: './medical-history.component.html',
  styleUrls: ['./medical-history.component.css']
})
export class MedicalHistoryComponent implements OnInit {
  public specs: Spec[];
  userId: number;
  specId: number;
  medicalHistories: MedicalHistory[];
  email: string;
  showMedicalHistoryForUser: boolean;
  constructor(private medicalHistoryService: MedicalHistoryService) { }

  ngOnInit() {

    this.showMedicalHistoryForUser = false;
    this.getEmailFromLoggedUser();
    this.getUserIdByEmail()
    this.getSpecs();
    this.findMedicalHistoryForUser();
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

  getUserIdAndMedicalHistory(){
    this.getUserIdByEmail();
    this.findMedicalHistoryForUser();
  }
  getEmailFromLoggedUser() {
    this.email = JSON.parse(localStorage.getItem('currentUser')).email;
  }

  getUserIdByEmail(): void {
    this.medicalHistoryService.getUserIdByEmail(this.email).subscribe(
      userId => {
        this.userId = userId;
        console.log("jestem w getuserIdByEmail, id usera: " + this.userId);
      },
      error => {
        console.log(error);
      }
    )
  }
  findMedicalHistoryForUser(): void {
    this.medicalHistoryService.findMedicalHistoryForUser(this.userId).subscribe(
      medicalHistories => {
        this.medicalHistories = medicalHistories;
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
}