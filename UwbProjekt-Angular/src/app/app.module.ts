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
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [UsersService, SpecsService, LoginService, DoctorsService, SpecdetailsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
