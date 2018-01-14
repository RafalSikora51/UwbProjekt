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
  checkboxValue: boolean = false;
  checkbox: boolean;

  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) { }

  ngOnInit() {
    //this.checkboxValue = false;
    this.authenticationService.logout();
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  log = '';

  logCheckbox(element: HTMLInputElement): void {
    this.log += `Checkbox ${element.value} was ${element.checked ? '' : 'un'}checked\n`;
    console.log(this.log);
  }

  clicked() {
    console.log('click')
  }

  changed(evt) {
    this.checkbox = evt.target.checked;
    console.log(evt.target.checked)
  }

  function() {
    if (this.checkboxValue === false) {
      this.checkboxValue = true;
      console.log('zmieniam z false na true');
    }
    else {
      this.checkboxValue = false;
      console.log('zmieniam z true na false');
    }
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

  doctorLogin() {
    this.error = '';
    this.loading = true;
    this.authenticationService.doctorLogin(this.model.email, this.model.token)
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

  onLogin() {
    console.log('wartosc checka to' + this.checkbox);
    if (this.checkbox) {
      console.log('Loguje jako lekarz.')
      this.doctorLogin();
    }
    else {
      console.log('Loguje jako pacjent/pani recepcjonistka.')
      this.login();
    }
  }


}
