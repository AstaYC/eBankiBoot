import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";
import {RoleGuard} from "./guard/roleGuard/role.guard";
import {HomeComponent} from "./home/home.component";

export const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [RoleGuard],
    data: {  role: 'ADMIN' }
  },

  {
    path: 'user',
    loadChildren: () => import('./user/user.module').then(m => m.UserModule),
    canActivate: [RoleGuard],
    data: { role: 'USER' }
  },

  {
    path:'employee',
    loadChildren: () =>import('./employee/employee.module').then(m => m.EmployeeModule),
    canActivate: [RoleGuard],
    data : { role: 'Employee' }
  },

  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
 ];
