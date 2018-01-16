import { Injectable } from '@angular/core';
import { OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';
import { Doctor } from '../shared/model/doctor';

@Injectable()
export class SpecdetailsService {

  private API_URL: any = '//localhost:9080/specs'
  constructor(private http: HttpClient) { }

  public getDoctorsBySpecId(id:number): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.API_URL}/${id}/doctors`)
      .pipe(
      tap(users => this.log(`fetched doctors`)),
      catchError(this.handleError('getDoctors', []))
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
