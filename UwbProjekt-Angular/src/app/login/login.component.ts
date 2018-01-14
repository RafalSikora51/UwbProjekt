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
  checkbox: boolean;

  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  clicked() {
    console.log('click')
  }

  changed(evt) {
    this.checkbox = evt.target.checked;
    console.log(evt.target.checked)
  }

  login() {
    this.error = '';
    this.loading = true;
    this.authenticationService.login(this.model.email, this.model.token)
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Zalogowano pomyślnie!');
          this.router.navigate([this.returnUrl]);
        } else {
          this.error = 'Nieprawidłowy email bądź hasło!';
          this.loading = false;
          this.toastr.error('Nieprawidłowy email bądź hasło!');
        }
      },
      () => {
        this.loading = false;
        this.error = 'Błąd połączenia z serwerem.'
        this.toastr.error('Błąd połączenia z serwerem.');
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
          this.toastr.success('Zalogowano pomyślnie!');
          this.router.navigate([this.returnUrl]);
        } else {
          this.error = 'Nieprawidłowy email bądź hasło!';
          this.loading = false;
          this.toastr.error('Nieprawidłowy email bądź hasło!');
        }
      },
      () => {
        this.loading = false;
        this.error = 'Błąd połączenia z serwerem.'
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }

  onLogin() {
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
