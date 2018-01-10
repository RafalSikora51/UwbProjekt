import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { UsersComponent } from './users/users.component';
import { UsersService} from './users/users.service';
import { AppComponent } from './app.component';
import { SpecsService } from './specs/specs.service';
import { SpecsComponent } from './specs/specs.component';


@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    SpecsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [UsersService, SpecsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
