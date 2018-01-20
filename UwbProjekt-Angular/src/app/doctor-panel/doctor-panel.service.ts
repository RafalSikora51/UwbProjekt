import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Doctor } from '../shared/model/doctor';
import { catchError, map, tap } from 'rxjs/operators';
import { OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { of } from 'rxjs/observable/of';
import { Apphour } from '../shared/model/apphour';
import { Appointments } from '../shared/model/appointments';

@Injectable()
export class DoctorPanelService {

  constructor(
    private http: HttpClient,
    private toastr: ToastrService
  ) { }

  private DOCTORS_API_URL: any = '//localhost:9080/doctors'
  private APPOINT_API_URL: any = '//localhost:9080/users'
  private USERID_API_URL: any = '//localhost:9080/users/email?email='
  private DOCTORID_API_URL: any = '//localhost:9080/doctors/email?email='

  public getDoctorIdByEmail(email: string): Observable<any> {
    return this.http.get<number>(this.DOCTORID_API_URL + email)
      .pipe(
      tap(id => this.log(`fetched id`)),
      catchError(this.handleError('getDoctorIdByEmail', []))
      );

  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }



}
