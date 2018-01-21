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
import { MedicalHistory } from '../shared/model/medicalhistory';
@Injectable()
export class UserPanelService {

  constructor(
    private http: HttpClient
  ) { }

  private DOCTORS_API_URL: any = '//localhost:9080/doctors'
  private APPOINT_API_URL: any = '//localhost:9080/users'
  private MEDICALHISTORY_URL: any = '//localhost:9080/users'
  private USERID_API_URL: any = '//localhost:9080/users/email?email='
  private APPHOUR_API_URL: any = '//localhost:9080/hours'


  public getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.DOCTORS_API_URL);
  }

  public getAllAppointmentsForId(id: number): Observable<Appointments[]> {
    return this.http.get<Appointments[]>(`${this.APPOINT_API_URL}/${id}/appointments`)
      .pipe(
      tap(appointments => this.log(`fetched appointments`)),
      catchError(this.handleError('getAllAppointments', []))
      );

  }

  getHourByHourId(id: number): Observable<Apphour> {
    return this.http.get<Apphour>(`${this.APPHOUR_API_URL}/${id}`)
      .pipe(
      tap(appointments => this.log(`fetched hour`))
      );
  }

  getAllHours(): Observable<Apphour[]> {
    return this.http.get<Apphour[]>(this.APPHOUR_API_URL)
      .pipe(
      tap(apphours => this.log(`fetched hours`)),
      catchError(this.handleError('getAllHours', []))
      );
  }

  public getUserIdByEmail(email: string): Observable<any> {
    return this.http.get<number>(this.USERID_API_URL + email)
      .pipe(
      tap(id => this.log(`fetched id`)),
      catchError(this.handleError('getUserIdByEmail', []))
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
