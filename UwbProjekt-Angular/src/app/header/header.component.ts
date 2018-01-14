import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';

import { AuthenticationService } from '../services/index';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authService: AuthenticationService) {
  }

  ngOnInit() {
  }

  isUserLoggedIn() { return JSON.parse(localStorage.getItem('currentUser')) };
  isAdminLoggedIn() { return JSON.parse(localStorage.getItem('currentAdmin')) };
  isDoctorLoggedIn() { return JSON.parse(localStorage.getItem('currentDoctor')) };
  isDoctorAdminLoggedIn() { return JSON.parse(localStorage.getItem('currentDoctorAdmin')) };

  isAnyoneLoggedIn() {
    return (this.isUserLoggedIn() || this.isAdminLoggedIn() || this.isDoctorLoggedIn() || this.isDoctorAdminLoggedIn());
  }

  onLogout() {
    this.authService.logout();
  }

}
