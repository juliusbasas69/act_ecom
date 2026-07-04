import { Routes } from '@angular/router';
import { loginGuard } from '../../core/guards/auth.guards';

export const authRoutes: Routes = [
  {
    path: 'login',
    loadComponent: () => {
      return import('../../features/auth/pages/login/login').then((m) => m.Login);
    },
    canActivate: [loginGuard],
  },
  {
    path: 'register',
    loadComponent: () => {
      return import('../../features/auth/pages/register/register').then((m) => m.Register);
    },
  },
];
