import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { Spec } from '../shared/model/spec';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

@Injectable()
export class SpecsStatsService {

  private SPEC_API_URL: string = '//localhost:9080/specs/'

  constructor(private http: HttpClient) { }

  public getSpecs(): Observable<Spec[]> {
    return this.http.get<Spec[]>(this.SPEC_API_URL)
      .pipe(
      tap(specs => this.log(`fetched specs`)),
      catchError(this.handleError('getSpecs', []))
      );
  }

  public getSpecsStats(): Observable<any[]> {
    return this.http.get<any[]>(this.SPEC_API_URL+'count')
      .pipe(
      tap(specs => this.log(`fetched specs info`)),
      catchError(this.handleError('getSpecsStats', []))
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
