import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule }    from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { UsersComponent } from './users/users.component';
import { UsersService} from './users/users.service';
import { AppComponent } from './app.component';
import { SpecsService } from './specs/specs.service';
import { SpecsComponent } from './specs/specs.component';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';
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
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [UsersService, SpecsService, LoginService, DoctorsService, SpecdetailsService,UserPanelService,AdminPanelService, DoctorPanelService],
  bootstrap: [AppComponent]
})
export class AppModule { }
