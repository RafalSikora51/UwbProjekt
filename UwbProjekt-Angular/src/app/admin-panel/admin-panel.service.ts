import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import { Doctor } from '../shared/model/doctor';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Spec } from '../shared/model/spec';
import { User } from '../shared/model/user';

@Injectable()
export class AdminPanelService {

  private CREATE_API_URL: any = '//localhost:9080/doctors?specName='
  private DOCTORS_API_URL: any = '//localhost:9080/doctors'
  private SPEC_API_URL: string = '//localhost:9080/specs/'
  private USERS_API_URL: string = '//localhost:9080/users'
  private ADD_SPEC_API_URL: string = '//localhost:9080/specs?specName='
  constructor(private http: HttpClient, private toastr: ToastrService) { }

  createDoctor(doctor: Doctor, specName: String): Observable<any> {
    return this.http.post(this.CREATE_API_URL + specName, doctor)
      .map((response: HttpResponse<any>) => {
        if (response['created'] === true) {
          return true;
        }
        else if (response['created'] === false && response['status'].toString() === 'CONFLICT') {
          this.toastr.error('Lekarz o takim peselu bądź adresie e-mail już istnieje!');
          return false;
        }
        else {
          return false;
        }
      });
  }

  createSpec(specName: String): Observable<any> {
    return this.http.post(this.ADD_SPEC_API_URL + specName, null)
      .map((response: HttpResponse<any>) => {
        if (response['created'] === true) {
          return true;
        }
        else if (response['created'] === false && response['status'].toString() === 'CONFLICT') {
          this.toastr.error('Specjalizacja o takiej nazwie już istnieje!');
          return false;
        }
        else {
          return false;
        }
      });
  }
  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.USERS_API_URL)
      .pipe(
      tap(users => this.log(`fetched users`)),
      catchError(this.handleError('getUsers', []))
      );
  }


  public getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.DOCTORS_API_URL)
      .pipe(
      tap(doctors => this.log(`fetched doctors`)),
      catchError(this.handleError('getDoctors', []))
      );
  }


  public getSpecs(): Observable<Spec[]> {
    return this.http.get<Spec[]>(this.SPEC_API_URL)
      .pipe(
      tap(specs => this.log(`fetched specs`)),
      catchError(this.handleError('getSpecs', []))
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

