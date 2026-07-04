import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../features/auth/auth.service';

export const loginGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  if (!auth.isLoggedIn()) {
    return true;
  }

  // Redirect based on role
  switch (auth.role()) {
    case 'ADMIN':
      return router.createUrlTree(['/admin/dashboard']);

    case 'USER':
      return router.createUrlTree(['/dashboard']);

    default:
      return router.createUrlTree(['/']);
  }
};

export const adminGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.role() === 'ADMIN' ? true : router.createUrlTree(['/login']);
};

export const userGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);

  return auth.role() === 'USER' ? true : router.createUrlTree(['/login']);
};
