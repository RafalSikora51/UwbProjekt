import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { SpecsComponent } from './specs/specs.component';

const routes: Routes = [
  { path: 'users', component: UsersComponent},
  { path: 'specs', component: SpecsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

//export const appRouting = RouterModule.forRoot(routes, { useHash: true });