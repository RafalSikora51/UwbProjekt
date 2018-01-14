import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class DoctorPanelGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if (localStorage.getItem('currentDoctor') || localStorage.getItem('currentDoctorAdmin')) {
            return true;
        } else {
            this.router.navigate(['/login']);
            return false;
        }
    }
}