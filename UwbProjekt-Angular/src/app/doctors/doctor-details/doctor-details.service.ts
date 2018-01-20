import { Injectable } from '@angular/core';
import { OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { Doctor } from './doctor';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Apphour } from '../../shared/model/apphour';

@Injectable()
export class DoctorDetailsService {

  API_URL: string = '//localhost:9080/doctors'
  CREATE_APPOINTMENT_API_URL: string = '//localhost:9080/appointments'
  doctor: Doctor;
  constructor(
    private http: HttpClient,
    private toastr: ToastrService
  ) { }

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

  public getDoctorById(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.API_URL}/${id}`).pipe(
      tap(_ => this.log(`fetched doctor id = ${id}`)),
      catchError(this.handleError<Doctor>(`getDoctorById id=${id}`))
    );
  }

  public getFreeHoursForDoctorFromGivenDay(id: number, date: String): Observable<Apphour[]> {
    return this.http.get<Apphour[]>(`${this.API_URL}/${id}/hours/free?date=` + date).pipe(
      tap(_ => this.log(`fetched taken hours for doctor = ${id} and day = ${date}`)),
      catchError(this.handleError<Apphour[]>(`getFreeHoursForDoctorFromGivenDay id=${id}`))
    );
  }
  
  createAppointment(email: String, docId: string, hourId: string, date: String): Observable<any> {
    return this.http.post(this.CREATE_APPOINTMENT_API_URL + `?userEmail=${email}&docId=${docId}&appHourId=${hourId}&date=${date}`, null)
      .map((response: HttpResponse<any>) => {
        console.log(response);
        if (response) {
          return true;
        }
        else {
          return false;
        }
      });
  }


}
