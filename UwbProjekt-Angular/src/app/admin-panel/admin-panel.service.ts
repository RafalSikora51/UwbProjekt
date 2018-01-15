import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { Spec } from '../shared/model/spec';
import { Doctor } from '../shared/model/doctor';

@Injectable()
export class AdminPanelService {

  specs: Spec;
  private CREATE_API_URL: any = '//localhost:9080/doctors?specName='
  constructor(private http: HttpClient) { }

  createDoctor(doctor: Doctor, specName: String) {
    return this.http.post(this.CREATE_API_URL + specName, doctor);
}




}

