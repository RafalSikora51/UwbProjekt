import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';



import { User } from '../shared/model/user';
@Injectable()
export class RegisterService {

  private API_URL: any = '//localhost:9080/users'

  constructor(private http: HttpClient) { }


  createUser(user: User) {
    return this.http.post(this.API_URL, user);
  }



}
