import { Route, Routes } from '@angular/router';
import { authRoutes } from './features/auth/auth.routes';
import { UserLayout } from './layouts/user/user-layout/user-layout';
import { AdminLayout } from './layouts/admin/admin-layout/admin-layout';
import { adminGuard, userGuard } from './core/guards/auth.guards';

export const routes: Routes = [
  ...authRoutes,
  {
    path: '',
    component: UserLayout,
    canActivate: [userGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => {
          return import('./features/dashboards/user-dashboard/user-dashboard').then(
            (m) => m.UserDashboard,
          );
        },
      },
    ],
  },
  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [adminGuard],
    children: [
      {
        path: 'dashboard',
        loadComponent: () => {
          return import('./features/dashboards/admin-dashboard/admin-dashboard').then(
            (m) => m.AdminDashboard,
          );
        },
      },
      {
        path: 'users',
        loadComponent: () => {
          return import('./features/users/pages/user-list/user-list').then((m) => m.UserList);
        },
      },
      {
        path: 'users/create',
        loadComponent: () => {
          return import('./features/users/pages/user-create/user-create').then((m) => m.UserCreate);
        },
      },
      {
        path: 'users/edit/:encryptedId',
        loadComponent: () => {
          return import('./features/users/pages/user-edit/user-edit').then((m) => m.UserEdit);
        },
      },
    ],
  },
];
