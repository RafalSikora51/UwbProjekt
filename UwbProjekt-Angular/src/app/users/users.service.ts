import { Injectable } from '@angular/core';

import {
  Http,
  Headers,
  RequestOptions,
  Response
} from '@angular/http';
import {
  Observable
} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import {
  User
} from '../shared/model/user';

@Injectable()
export class UsersService {
  private static readonly API_URL: string = '/users/'

  constructor(private http: Http) { }

  private extractData(res: Response) {
    const body = res.json();
    console.log(res.text());
    return body || {};
  }
  
  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg.toString());
    return Observable.throw(errMsg);
  }
  public getUsers(): Observable<User[]> {
    const url = UsersService.API_URL;
    return this.http.get(url)
      .map(this.extractData)
      .catch(this.handleError);
  }
 
}
