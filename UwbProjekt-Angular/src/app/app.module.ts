import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { UsersComponent } from './users/users.component';
import { UsersService} from './users/users.service';
import { AppComponent } from './app.component';
import { SpecsService } from './specs/specs.service';
import { SpecsComponent } from './specs/specs.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { LoginService } from './login/login.service';


@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    SpecsComponent,
    DashboardComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [UsersService, SpecsService, LoginService],
  bootstrap: [AppComponent]
})
export class AppModule { }
