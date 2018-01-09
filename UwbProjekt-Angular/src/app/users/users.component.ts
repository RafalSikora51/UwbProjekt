import { Component, OnInit } from '@angular/core';
import { UsersService} from './users.service';

import { User} from '../shared/model/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  providers: [UsersService]
})
export class UsersComponent implements OnInit {

  users: User[];

  constructor(
    private userService: UsersService) {
  }

  ngOnInit() {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers().subscribe(
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
