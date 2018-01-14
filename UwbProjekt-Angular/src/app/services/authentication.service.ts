import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {

  private static readonly API_LOGIN: string = '//localhost:9080/login'
  private static readonly API_DOCTORLOGIN: string = '//localhost:9080/doctorlogin'
  constructor(private http: HttpClient, private toastr: ToastrService,) { }

  login(email: string, password: string): Observable<any> {

    return this.http.post(AuthenticationService.API_LOGIN + '?email=' + email + '&token=' + password, null)
      .map((response: HttpResponse<any>) => {
        if (response['canLogin'].toString() === 'true') {
          if (response['admin'].toString() === 'true') {
            localStorage.setItem('currentAdmin', JSON.stringify({
              email: email,
              token: password
            }));
          }

          else {
            localStorage.setItem('currentUser', JSON.stringify({
              email: email,
              token: password
            }));
          }
          return true;
        }
        else {
          return false;
        }
      });
  }

  doctorLogin(email: string, password: string): Observable<any> {

    return this.http.post(AuthenticationService.API_DOCTORLOGIN + '?email=' + email + '&token=' + password, null)
      .map((response: HttpResponse<any>) => {
        if (response['canLogin'].toString() === 'true') {
          if (response['admin'].toString() === 'true') {
            localStorage.setItem('currentDoctorAdmin', JSON.stringify({
              email: email,
              token: password
            }));
          }

          else {
            localStorage.setItem('currentDoctor', JSON.stringify({
              email: email,
              token: password
            }));
          }
          return true;
        }
        else {
          return false;
        }
      });
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('currentAdmin');
    localStorage.removeItem('currentDoctor');
    localStorage.removeItem('currentDoctorAdmin');
    this.toastr.success('Wylogowano pomyślnie!');

  }
}