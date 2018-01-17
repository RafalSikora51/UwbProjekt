import { Injectable } from '@angular/core';
import { OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { Doctor } from './doctor';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

@Injectable()
export class DoctorDetailsService {

  API_URL: string = '//localhost:9080/doctors'
  doctor: Doctor;
  constructor(private http: HttpClient) { }


  public getDoctorById(id: number): Observable<Doctor> {
    return this.http.get<Doctor>(`${this.API_URL}/${id}`).pipe(
      tap(_ => this.log(`fetched doctor id = ${id}`)),
      catchError(this.handleError<Doctor>(`getDoctorById id=${id}`))
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
