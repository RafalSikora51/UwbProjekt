import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import { Doctor } from '../shared/model/doctor';
import 'rxjs/add/operator/map'

@Injectable()
export class AdminPanelService {

  private CREATE_API_URL: any = '//localhost:9080/doctors?specName='
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




}

