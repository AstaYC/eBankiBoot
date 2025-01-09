import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";
import {AdminDashboardComponent} from "./admin-dashboard/admin-dashboard.component";
import {RoleGuard} from "./guard/roleGuard/role.guard";
import {UserDashboardComponent} from "./user-dashboard/user-dashboard.component";
import {EmployeeDashboardComponent} from "./employee-dashboard/employee-dashboard.component";


export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin', component: AdminDashboardComponent, canActivate: [RoleGuard], data: { role: 'ADMIN' } },
  { path: 'user', component: UserDashboardComponent, canActivate: [RoleGuard], data: { role: 'USER' } },
  { path: 'employee', component: EmployeeDashboardComponent, canActivate: [RoleGuard], data: { role: 'EMPLOYEE' } },
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];
