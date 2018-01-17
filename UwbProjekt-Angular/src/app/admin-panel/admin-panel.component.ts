import { Component, OnInit } from '@angular/core';
import { AdminPanelService } from '../admin-panel/admin-panel.service';
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from '@angular/router';
import { Input } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Doctor } from '../shared/model/doctor';
import { Spec } from '../shared/model/spec';
import { User } from '../shared/model/user';
@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css'],

})
export class AdminPanelComponent implements OnInit {

  showAddDoctor: boolean;
  showDoctors: boolean;
  showAddSpec: boolean;
  showUsers: boolean;
  model: any = {};
  doctors: Doctor[];
  specs: Spec[];
  users: User[];
  doctorSpecs: any = {};
  specModel: any = {};
  loading = false;
  checkbox: boolean;
  returnUrl: string;
  createDocForm: any;
  createSpecForm: any;

  error: String = '';
  constructor(private route: ActivatedRoute,
    private router: Router,
    private adminPanelService: AdminPanelService,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    this.showAddDoctor = false;
    this.showAddSpec = false;
    this.showDoctors = false;
    this.showUsers = false;
    this.getDoctors();
    this.getSpecs();
    this.getUsers();
  }

  getDoctors(): void {
    this.adminPanelService.getDoctors().subscribe(
      doctors => {
        this.doctors = doctors;
      },
      error => {
        console.log(error);
      }
    )
  }

  createDoctor() {
    this.loading = true;
    this.adminPanelService.createDoctor(this.model, this.specModel.specName)
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Lekarz został dodany pomyślnie! E-mail z wygenerowanym hasłem został wysłany.');
          this.createDocForm = document.getElementById("doctorForm");
          this.createDocForm.reset();
        } else {
          this.error = 'Nieprawidłowe dane podczas dodawania lekarza!';
          this.loading = false;
          this.toastr.error('Nieprawidłowe dane podczas dodawania lekarza!');
        }
      },
      () => {
        this.loading = false;
        this.error = 'Błąd połączenia z serwerem.'
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }
  createSpec() {
    this.loading = true;
    this.adminPanelService.createSpec(this.specModel.specName)
      .subscribe(
      result => {
        if (result === true) {
          this.toastr.success('Specjalizacja dodana pomyślnie!');
          this.createSpecForm = document.getElementById("specForm");
          this.createSpecForm.reset();
        } else {
          this.error = 'Nieprawidłowe dane podczas dodawania specjalizacji!';
          this.loading = false;
          this.toastr.error('Nieprawidłowe dane podczas dodawania specjalizacji!');
        }
      },
      () => {
        this.loading = false;
        this.error = 'Błąd połączenia z serwerem.'
        this.toastr.error('Błąd połączenia z serwerem.');
      },
      () => console.log('done!'));
  }

  getSpecs(): void {
    this.adminPanelService.getSpecs().subscribe(
      specs => {
        this.specs = specs;
        console.table(this.specs);
      },
      error => {
        console.log(error);
      }
    );
  }

  getUsers(): void {
    this.adminPanelService.getUsers().subscribe(
      users => {
        this.users = users;
        console.table(this.users);
      },
      error => {
        console.log(error);
      }
    );
  }


  clicked() {
    console.log('click')
  }

  changed(evt) {
    this.checkbox = evt.target.checked;
    console.log(evt.target.checked)
  }

  onSubmit() {
    if (this.checkbox) {
      this.model.admin = true;
      console.log('Tworze z prawami admina.')
      this.createDoctor();
      this
    }
    else {
      this.model.admin = false;
      console.log('Tworze bez praw admina.')
      this.createDoctor();
    }
  }
  onSpecSubmit() {
    this.createSpec();
  }

  addDoctorEnable() {
    if (this.showAddDoctor == false)
      this.showAddDoctor = true;
    else {
      this.showAddDoctor = false;
      this.createDocForm = document.getElementById("doctorForm");
      this.createDocForm.reset();
    }

  }

  showDoctorsEnable() {
    if (this.showDoctors == false)
      this.showDoctors = true;
    else {
      this.showDoctors = false;
    }
  }
  showUsersEnable() {
    if (this.showUsers == false)
      this.showUsers = true;
    else {
      this.showUsers = false;
    }
  }
  addSpecEnable() {
    if (this.showAddSpec == false)
      this.showAddSpec = true;
    else {
      this.showAddSpec = false;
      this.createSpecForm = document.getElementById("specForm");
      this.createSpecForm.reset();
    }

  }


}
