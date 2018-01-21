import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { SpecsComponent } from './specs/specs.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { SpecdetailsComponent } from './specdetails/specdetails.component';

import { HomeLayoutComponent } from './home-layout/home-layout.component';
import { UserPanelComponent } from './user-panel/user-panel.component';
import { AdminPanelComponent } from './admin-panel/admin-panel.component';
import { DoctorPanelComponent } from './doctor-panel/doctor-panel.component';

import { AuthGuard } from './guard/index';
import { DoctorPanelGuard } from './guard/doctorpanel.guard';
import { AdminPanelGuard } from './guard/adminpanel.guard';
import { UserDetailsGuard } from './guard/userdetails.guard';
import { LoggedUserGuard } from './guard/loggeduser.guard';

import { RegisterComponent } from './register/register.component';
import { DoctorDetailsComponent } from './doctors/doctor-details/doctor-details.component';
import { MedicalHistoryComponent } from './medical-history/medical-history.component';
import { UserDetailsComponent } from './users/user-details/user-details.component';

const routes: Routes = [
  {
    path: '', component: HomeLayoutComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'home', component: HomeComponent },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'specs', component: SpecsComponent, pathMatch: 'full' },
      { path: 'specs/:id', component: SpecdetailsComponent, },
      { path: 'userpanel', component: UserPanelComponent, canActivate: [AuthGuard] },
      { path: 'doctorpanel', component: DoctorPanelComponent, canActivate: [DoctorPanelGuard] },
      { path: 'adminpanel', component: AdminPanelComponent, canActivate: [AdminPanelGuard] },
      { path: 'users/:id', component: UserDetailsComponent, canActivate: [UserDetailsGuard] },
      { path: 'doctors/:id', component: DoctorDetailsComponent, canActivate: [LoggedUserGuard] },
    ]
  },

  { path: 'doctors', component: DoctorsComponent },

  { path: '**', redirectTo: '' },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
