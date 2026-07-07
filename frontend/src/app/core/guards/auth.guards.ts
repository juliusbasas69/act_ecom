import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../features/auth/auth.service';

export const loginGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  // User is NOT logged in -> allow access to login page
  if (!auth.isLoggedIn()) {
    return true;
  }

  // User IS logged in -> redirect away from login page
  switch (auth.role()) {
    case 'ADMIN':
      return router.createUrlTree(['/admin/dashboard']);

    case 'USER':
      return router.createUrlTree(['/dashboard']);

    default:
      auth.logout();
      return router.createUrlTree(['/login']);
  }
};

export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isTokenExpired()) {
    auth.logout('expired');
    console.log('OKY');
    return router.createUrlTree(['/login']);
  }

  return auth.role() === 'ADMIN' ? true : router.createUrlTree(['/login']);
};

export const userGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (auth.isTokenExpired()) {
    auth.logout('expired');
    return router.createUrlTree(['/login']);
  }

  return auth.role() === 'USER' ? true : router.createUrlTree(['/login']);
};
