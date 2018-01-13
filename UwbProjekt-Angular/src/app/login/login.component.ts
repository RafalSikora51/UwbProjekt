import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

import { AlertService, AuthenticationService } from '../services/index';

@Component({
  selector: 'app-login',
  moduleId: module.id,
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  model: any = {};
  loading = false;
  returnUrl: string;

  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) { }

  ngOnInit() {
    this.authenticationService.logout();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  login() {
    this.error = '';
    this.loading = true;
    this.authenticationService.login(this.model.email, this.model.token)
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Logged in');
          this.router.navigate([this.returnUrl]);
        } else {
          this.error = 'Username or password is incorrect';
          this.loading = false;
          this.toastr.error('Username or password is incorrect');
        }
      },
      () => {
        this.loading = false;
        this.error = 'Cannot connect'
        this.toastr.error('Cannot connect');
      },
      () => console.log('done!'));
  }
}
