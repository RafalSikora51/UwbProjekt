import { Component, OnInit } from '@angular/core';

import { User } from '../shared/model/user';
import { UsersService } from '../users/users.service';

@Component({
    selector: 'app-home',
    moduleId: module.id,
    templateUrl: 'home.component.html',
    styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
    currentUser: User;

    constructor(private userService: UsersService) {
    }

    ngOnInit() {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    }

    
}