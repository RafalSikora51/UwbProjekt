import { Component, OnInit } from '@angular/core';
import { UsersService } from './users.service';

import { ToastrService } from 'ngx-toastr';

import { User } from '../shared/model/user';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css'],
  providers: [UsersService]
})
export class UsersComponent implements OnInit {

  users: User[];

  constructor(
    private userService: UsersService,
    private toastr: ToastrService) {
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

  changeAdminRights(id: number) {
    this.userService.changeAdminRights(id).subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Prawa administratora zmieniono pomyślnie!');
          this.getUsers();
        } else {
          this.toastr.error('Nie udało się zmienić praw administratora!');
        }
      },
      () => {
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }

  onSubmit(id: number): void {
    this.changeAdminRights(id);
  }

}
