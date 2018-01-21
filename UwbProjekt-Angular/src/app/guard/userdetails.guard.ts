import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class UserDetailsGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (localStorage.getItem('currentAdmin') || localStorage.getItem('currentDoctorAdmin') || localStorage.getItem('currentDoctor')) {
      return true;
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }

}