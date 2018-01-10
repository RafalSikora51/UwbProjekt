import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { Spec } from '../shared/model/spec';

import { of } from 'rxjs/observable/of';
import { catchError, map, tap } from 'rxjs/operators';


@Injectable()
export class SpecsService {

  private static readonly API_URL:string ='//localhost:9080/specs'

  constructor(private http: HttpClient) { }

  private extractData(res: Response) {
    const body = res.json();
    console.log(res.text());
    return body || {};
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); 
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }

  public getSpecs(): Observable<Spec[]>{
    return this.http.get<Spec[]>(SpecsService.API_URL)
    .pipe(
      tap(specs => this.log(`fetched specs`)),
      catchError(this.handleError('getSpecs', []))
    );
  }

  private log(message: string) {
    console.log(message);
  }

}
