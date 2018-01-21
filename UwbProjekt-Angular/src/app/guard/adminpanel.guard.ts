import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class AdminPanelGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (localStorage.getItem('currentAdmin') || localStorage.getItem('currentDoctorAdmin')) {
            return true;
        } else {
            this.router.navigate(['/']);
            return false;
        }
    }

}