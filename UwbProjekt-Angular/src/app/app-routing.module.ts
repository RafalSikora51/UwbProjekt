import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { SpecsComponent } from './specs/specs.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { SpecdetailsComponent } from './specdetails/specdetails.component';

import { HomeLayoutComponent } from './home-layout/home-layout.component';

const routes: Routes = [
  { path: '', component: HomeLayoutComponent,
  children: [
    { path: '', component: HomeComponent},
    { path: 'home', component: HomeComponent},
    { path: 'doctors', component: DoctorsComponent, pathMatch: 'full'},
    { path: 'specs', component: SpecsComponent, pathMatch: 'full' },
  ] },


  { path: 'specdetails', component: SpecdetailsComponent },
  { path: 'users', component: UsersComponent },
  { path: 'login', component: LoginComponent }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
