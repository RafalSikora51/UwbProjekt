import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Doctor } from '../shared/model/doctor';
import { catchError, map, tap } from 'rxjs/operators';
import { OnInit } from '@angular/core';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { of } from 'rxjs/observable/of';
import { Apphour } from '../shared/model/apphour';
import { Appointments } from '../shared/model/appointments';

@Injectable()
export class DoctorPanelService {

  constructor(
    private http: HttpClient
  ) { }

  //private DOCTORS_API_URL: any = '//localhost:9080/doctors'
  //private USER_APPOINT_API_URL: any = '//localhost:9080/users'
  //private USERID_API_URL: any = '//localhost:9080/users/email?email='
  private DOCTORID_API_URL: any = '//localhost:9080/doctors/email?email='
  private DOCTOR_APPOINT_API_URL: any = '//localhost:9080/doctors'
  private APPHOUR_API_URL: any = '//localhost:9080/hours'
  private APPPROCESS_API_URL: any = '//localhost:9080/appointments'

  public editAppointmentProcess(id: number, appProcess: string): Observable<any> {
    console.log("Jestem w doctor service");
    console.log("Wartosc ID i appProcess:");
    console.log(id+"   "+appProcess);
    return this.http.put(`${this.APPPROCESS_API_URL}/${id}/process?appProcess=${appProcess}`, null)
    .pipe(
      tap(id => this.log(`fetched id`)),
      catchError(this.handleError('editAppointmentProcess', []))
      );
  }

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

  public getAllAppointmentsForDoctor(id: number): Observable<Appointments[]> {
    return this.http.get<Appointments[]>(`${this.DOCTOR_APPOINT_API_URL}/${id}/appointments`)
      .pipe(
      tap(appointments => this.log(`fetched appointments`)),
      catchError(this.handleError('getAllAppointmentsForDoctor', []))
      );
  }

  getAllHours(): Observable<Apphour[]> {
    return this.http.get<Apphour[]>(this.APPHOUR_API_URL)
      .pipe(
      tap(apphours => this.log(`fetched hours`)),
      catchError(this.handleError('getAllHours', []))
      );
  }



}
