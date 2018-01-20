import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../users/users.service';

import { ToastrService } from 'ngx-toastr';
import { DoctorPanelComponent } from '../doctor-panel.component'

import { User } from '../../shared/model/user';

@Component({
  selector: 'app-doctor-users',
  templateUrl: './doctor-users.component.html',
  styleUrls: ['./doctor-users.component.css']
})
export class DoctorUsersComponent implements OnInit {

  users: User[];


  constructor(
    private userService: UsersService,
    private toastr: ToastrService,
    private doctorPanelComponent: DoctorPanelComponent
  ) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsersForDoctor(this.doctorPanelComponent.id).subscribe(
      users => {
        this.users = users;
        console.table(this.users);
      },
      error => {
        console.log(error);
      }
    );
  }

}
