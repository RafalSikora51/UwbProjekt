import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Doctor } from '../shared/model/doctor';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable()
export class UserPanelService {


  constructor(private http: HttpClient) { }

  private DOCTORS_API_URL: any = '//localhost:9080/doctors'

  public getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(this.DOCTORS_API_URL);
  }

  private log(message: string) {
    console.log(message);
  }


}
