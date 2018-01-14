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

  isUserLoggedIn$: Observable<boolean>;
  isAdminLoggedIn$: Observable<boolean>;
  isDoctorLoggedIn$: Observable<boolean>;
  isDoctorAdminLoggedIn$: Observable<boolean>;
  isAnyoneLoggedIn$: Observable<boolean>;

  constructor(private authService: AuthenticationService) {
  }

  ngOnInit() {
    this.isUserLoggedIn$ = Observable.of(false);
    this.isAdminLoggedIn$ = Observable.of(false);
    this.isDoctorLoggedIn$ = Observable.of(false);
    this.isDoctorAdminLoggedIn$ = Observable.of(false);
    this.isAnyoneLoggedIn$ = Observable.of(false);


    if (localStorage.getItem('currentUser')) {
      this.isUserLoggedIn$ = Observable.of(true);
      this.isAnyoneLoggedIn$ = Observable.of(true);
    }
    else if (localStorage.getItem('currentAdmin')) {
      this.isAdminLoggedIn$ = Observable.of(true);
      this.isAnyoneLoggedIn$ = Observable.of(true);
    }
    else if (localStorage.getItem('currentDoctor')) {
      this.isDoctorLoggedIn$ = Observable.of(true);
      this.isAnyoneLoggedIn$ = Observable.of(true);
    }
    else if (localStorage.getItem('currentDoctorAdmin')) {
      this.isDoctorAdminLoggedIn$ = Observable.of(true);
      this.isAnyoneLoggedIn$ = Observable.of(true);
    }


  }

  onLogout() {
    this.authService.logout();
  }

}
