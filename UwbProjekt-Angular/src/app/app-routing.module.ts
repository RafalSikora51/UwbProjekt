import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { SpecsComponent } from './specs/specs.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { DoctorsComponent } from './doctors/doctors.component';
import { SpecdetailsComponent } from './specdetails/specdetails.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UsersComponent },
  { path: 'specs', component: SpecsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'doctors', component: DoctorsComponent },
  {path: 'specdetails', component:SpecdetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
