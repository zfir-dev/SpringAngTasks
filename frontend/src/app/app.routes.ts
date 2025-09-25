import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { RegisterComponent } from './features/register/register.component';
import { TaskDashboardComponent } from './features/tasks/task-dashboard/task-dashboard.component';
import { authGuard } from './core/auth-guard.guard';

export const routes: Routes = [
  { path: '', component: TaskDashboardComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: '' }
];
