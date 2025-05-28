import { Routes } from '@angular/router';
import { RegisterComponent } from './Auth/register/register.component';
import { LoginComponent } from './Auth/login/login.component';
import { DashboardComponent } from './Module/admin/components/dashboard/dashboard.component';
export const routes: Routes = [
    {path: 'register', component: RegisterComponent},
     {path: 'login', component: LoginComponent},
     {
    path: 'admin',
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      }
    ]
  }
];
