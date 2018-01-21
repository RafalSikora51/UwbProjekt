import { Component, OnInit } from '@angular/core';

import { Appointments } from '../../shared/model/appointments';
import { Apphour } from '../../shared/model/apphour';
import { User } from '../../shared/model/user';
import { MedicalHistory } from '../../shared/model/medicalhistory';
import { UsersService } from '../users.service';
import { Router, ActivatedRoute, Params } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  showMedicalHistory: boolean;
  showUserDetails: boolean;

  appointments: Appointments[];
  medicalHistories: MedicalHistory[];
  email: string;
  userId: number;

  user: User;
  constructor(
    private usersService: UsersService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.showMedicalHistory = false;
    this.showUserDetails = false;
    this.getUserById();
  }

  getUserById(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.usersService.getUserById(id).subscribe(
      user => {
        this.user = user;
        console.table(this.user);
      },
      error => {
        console.log(error);
      }
    );
  }

  showMedicalHistoryEnable() {
    if (this.showMedicalHistory == false) {
      this.showMedicalHistory = true;
    } else {
      this.showMedicalHistory = false;
    }
  }

  showUserDetailsEnable() {
    if (this.showUserDetails == false) {
      this.showUserDetails = true;
    } else {
      this.showUserDetails = false;
    }
  }

}
