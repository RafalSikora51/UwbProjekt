import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { Doctor } from '../shared/model/doctor';
import { Spec } from '../shared/model/spec';


@Injectable()
export class DoctorsService {
  private DOCTORS_API_URL: any = '//localhost:9080/doctors'
  private SPECS_API_URL: string = '//localhost:9080/specs'
  constructor(private http: HttpClient) { }

  public getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.DOCTORS_API_URL)
      .pipe(
      tap(doctors => this.log(`fetched doctors`)),
      catchError(this.handleError('getDoctors', []))
      );
  }

  public getSpecs(): Observable<Spec[]> {
    return this.http.get<Spec[]>(this.SPECS_API_URL)
      .pipe(
      tap(specs => this.log(`fetched specs`)),
      catchError(this.handleError('getSpecs', []))
      );
  }

  public changeAdminRights(id: number): Observable<any> {
    return this.http.post(this.DOCTORS_API_URL + `/${id}/admin`, null)
      .pipe(
      catchError(this.handleError('changeAdminRights', []))
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
