import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { UsersComponent } from './users/users.component';
import { UsersService } from './users/users.service';
import { AppComponent } from './app.component';
import { SpecsService } from './specs/specs.service';
import { SpecsComponent } from './specs/specs.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { DoctorsService } from './doctors/doctors.service';
import { SpecdetailsComponent } from './specdetails/specdetails.component';
import { SpecdetailsService } from './specdetails/specdetails.service';
import { HeaderComponent } from './header/header.component';
import { HomeLayoutComponent } from './home-layout/home-layout.component';
import { UserPanelComponent } from './user-panel/user-panel.component';
import { UserPanelService } from './user-panel/user-panel.service';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { AdminPanelService } from './admin-panel/admin-panel.service';
import { DoctorPanelComponent } from './doctor-panel/doctor-panel.component';
import { DoctorPanelService } from './doctor-panel/doctor-panel.service';
import { AlertComponent } from './alert/alert.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './guard/index';
import { DoctorPanelGuard } from './guard/doctorpanel.guard';
import { AdminPanelGuard } from './guard/adminpanel.guard';
import { UserDetailsGuard } from './guard/userdetails.guard';
import { LoggedUserGuard } from './guard/loggeduser.guard';
import { AlertService, AuthenticationService, } from './services/index';
import { RegisterService } from './register/register.service';
import { DoctorDetailsService } from './doctors/doctor-details/doctor-details.service';
import { DoctorDetailsComponent } from './doctors/doctor-details/doctor-details.component';
import { SpecsStatsComponent } from './specs-stats/specs-stats.component';
import { SpecsStatsService } from './specs-stats/specs-stats.service';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NgxPaginationModule } from 'ngx-pagination';
import {
  MatDatepickerModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatRippleModule,
  MatNativeDateModule
} from '@angular/material';
import { DoctorUsersComponent } from './doctor-panel/doctor-users/doctor-users.component';
import { MedicalHistoryComponent } from './medical-history/medical-history.component';
import { MedicalHistoryService } from './medical-history/medical-history.service';
import { DoctorAppointmentsComponent } from './doctor-panel/doctor-appointments/doctor-appointments.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';
import { LOCALE_ID } from '@angular/core';


@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    SpecsComponent,
    LoginComponent,
    HomeComponent,
    DoctorsComponent,
    SpecdetailsComponent,
    HeaderComponent,
    HomeLayoutComponent,
    UserPanelComponent,
    AdminPanelComponent,
    DoctorPanelComponent,
    AlertComponent,
    RegisterComponent,
    DoctorDetailsComponent,
    SpecsStatsComponent,
    DoctorUsersComponent,
    MedicalHistoryComponent,
    DoctorAppointmentsComponent,
    UserDetailsComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    NoopAnimationsModule,
    NgxPaginationModule,
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatRippleModule,
  ],
  providers: [UsersService, SpecsService, DoctorsService,
    SpecdetailsService, UserPanelService, AdminPanelService,
    DoctorPanelService, AuthGuard, DoctorPanelGuard,
    AdminPanelGuard, UserDetailsGuard, LoggedUserGuard, AlertService, AuthenticationService,
    RegisterService, DoctorDetailsService, SpecsStatsService,
    SpecsComponent, DoctorsComponent, MedicalHistoryService,
    SpecsComponent, MedicalHistoryService, UserPanelComponent, DoctorPanelComponent],

  bootstrap: [AppComponent]
})
export class AppModule { }
