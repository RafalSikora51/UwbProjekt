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
import { Spec } from '../shared/model/spec';
import { MedicalHistory } from '../shared/model/medicalhistory';
@Injectable()
export class MedicalHistoryService {

  constructor(private http: HttpClient) { }

  private SPEC_URL: string = '//localhost:9080/specs'
  private MEDICALHISTORY_URL: any = '//localhost:9080/users'
  private USERID_API_URL: any = '//localhost:9080/users/email?email='

  
  public getSpecs(): Observable<Spec[]> {
    return this.http.get<Spec[]>(this.SPEC_URL)
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

  public getUserIdByEmail(email: string): Observable<any> {
    return this.http.get<number>(this.USERID_API_URL + email)
      .pipe(
      tap(id => this.log(`fetched id`)),
      catchError(this.handleError('getUserIdByEmail', []))
      );

  }

  public findMedicalHistoryForUser(userId: number): Observable<MedicalHistory[]> {
    return this.http.get<MedicalHistory[]>(`${this.MEDICALHISTORY_URL}/${userId}/histories`)
      .pipe(
      tap(medicalHistory => this.log(`feteched medical histories`)),
      catchError(this.handleError('findMedicalHistoryForUser', []))
      );
  }
  private log(message: string) {
    console.log(message);
  }

}
