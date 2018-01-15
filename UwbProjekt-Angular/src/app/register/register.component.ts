import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { RegisterService } from '../register/register.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-register',
    templateUrl: 'register.component.html',
    styleUrls: ['./register.component.css']
})

export class RegisterComponent {
    model: any = {};

    constructor(
        private router: Router,
        private registerService: RegisterService,
        private toastr: ToastrService) { }

    createUser() {
        this.registerService.createUser(this.model)
            .subscribe(
            data => {
                if (this.model.countryId >= 9) {
                    this.toastr.success('Rejestracja przebiegła pomyślnie! Sprawdź e-mail aby otrzymać hasło');
                    this.router.navigate(['/login']);
                }
            },
            error => {
                this.toastr.error('Błąd podczas rejestracji');
            });
    }

    onSubmit() {
        this.createUser();
    }



}
