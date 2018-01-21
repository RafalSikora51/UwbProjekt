import { Component, OnInit } from '@angular/core';
import { UsersService } from '../../users/users.service';
import { ActivatedRoute } from '@angular/router';
import { DoctorPanelComponent } from '../doctor-panel.component'
import { User } from '../../shared/model/user';

@Component({
  selector: 'app-doctor-users',
  templateUrl: './doctor-users.component.html',
  styleUrls: ['./doctor-users.component.css']
})
export class DoctorUsersComponent implements OnInit {

  users: User[];
  currentPage = 1;


  constructor(
    private userService: UsersService,
    private doctorPanelComponent: DoctorPanelComponent,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    let id;
    if (this.route.snapshot.paramMap.get('id')) {
      id = this.route.snapshot.paramMap.get('id');
    } else {
      id = this.doctorPanelComponent.id;
    }
    this.userService.getUsersForDoctor(id).subscribe(
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
