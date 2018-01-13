import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map'

@Injectable()
export class AuthenticationService {

  private static readonly API_URL: string = '//localhost:9080/login'

  constructor(private http: HttpClient) { }

  login(email: string, password: string): Observable<boolean> {

    return this.http.post(AuthenticationService.API_URL + '?email=' + email + '&token=' + password, null)
      .map((response: HttpResponse<any>) => {
        console.log(response);
        if (response.toString() === 'true') {
          console.log('jestem przy storage');
          sessionStorage.setItem('currentUser', JSON.stringify({
            email: email,
            token: password
          }));
          return true;
        } else {
          return false;
        }
      });
  }

  logout() {
    sessionStorage.removeItem('currentUser');
  }
}