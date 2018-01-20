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
  private USERS_API_URL: string = '//localhost:9080/users'
  private DOCTORS_API_URL: string = '//localhost:9080/doctors'

  constructor(private http: HttpClient) { }

  create(user: User) {
    return this.http.post(this.USERS_API_URL, user);
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

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.USERS_API_URL)
      .pipe(
      tap(users => this.log(`fetched users`)),
      catchError(this.handleError('getUsers', []))
      );
  }

  public getUsersForDoctor(id: number): Observable<User[]> {
    return this.http.get<User[]>(this.DOCTORS_API_URL + `/${id}/users`)
      .pipe(
      tap(users => this.log(`fetched users`)),
      catchError(this.handleError('getUsersForDoctor', []))
      );
  }

  public changeAdminRights(id: number): Observable<any>{
    return this.http.post(this.USERS_API_URL + `/${id}/admin` ,null)
    .pipe(
      catchError(this.handleError('changeAdminRights', []))
    );
  }

}
