import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { RegisterService } from '../register/register.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'app-register',
    templateUrl: 'register.component.html',
    styleUrls: ['./register.component.css']
})

export class RegisterComponent {
    model: any = {};
    loading = false;
    error: String = '';

    constructor(
        private router: Router,
        private registerService: RegisterService,
        private toastr: ToastrService) { }

    createUser() {
        this.loading = true;
        this.registerService.createUser(this.model)
            .subscribe(
            result => {
                if (result === true) {
                    this.toastr.success('Rejestracja przebiegła pomyślnie! Sprawdź e-mail aby otrzymać hasło.');
                    this.router.navigate(['/login']);
                } else {
                    this.error = 'Nieprawidłowe dane podczas rejestracji!';
                    this.loading = false;
                    this.toastr.error('Nieprawidłowe dane podczas rejestracji!');
                }
            },
            () => {
                this.loading = false;
                this.error = 'Błąd połączenia z serwerem.'
                this.toastr.error('Błąd połączenia z serwerem.');
            },
            () => console.log('done!'));
    }

    onSubmit() {
        this.createUser();
    }

}
