import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { User } from '../shared/model/user';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs/observable/of';

@Injectable()
export class UsersService {
  private static readonly API_URL: string = '//localhost:9080/users'

  constructor(private http: HttpClient) { }


  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(UsersService.API_URL)
      .pipe(
      tap(users => this.log(`fetched users`)),
      catchError(this.handleError('getUsers', []))
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
