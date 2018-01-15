import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { ToastrService } from 'ngx-toastr';
import { User } from '../shared/model/user';
import 'rxjs/add/operator/map'


@Injectable()
export class RegisterService {

  private API_URL: any = '//localhost:9080/users'

  constructor(private http: HttpClient, private toastr: ToastrService) { }

  createUser(user: User): Observable<any> {
    return this.http.post(this.API_URL, user)
      .map((response: HttpResponse<any>) => {
        if (response['created'] === true) {
          return true;
        }
        else if (response['created'] === false && response['status'].toString() === 'CONFLICT') {
          this.toastr.error('Użytkownik o takim peselu bądź adresie e-mail już istnieje!');
          return false;
        }
        else {
          return false;
        }
      });
  }

}
