import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';
import { Spec } from '../shared/model/spec';
import { HttpParams } from '@angular/common/http';

import { Doctor } from '../shared/model/doctor';

@Injectable()
export class AdminPanelService {

  specs: Spec;
  private API_URL: any = '//localhost:9080/doctors'

  constructor(private http: HttpClient) { }

  createDoctor(doctor: Doctor): Observable<Doctor> {
    return this.http.post(this.API_URL,doctor,{params:new HttpParams().set('specName',this.specs.specName)})
     .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }


}

