import { Component, inject } from '@angular/core';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-user-dashboard',
  imports: [],
  templateUrl: './user-dashboard.html',
  styleUrl: './user-dashboard.css',
})
export class UserDashboard {
  authService = inject(AuthService);

  get initials(): string {
    const fullName = this.authService.fullName();

    if (!fullName) {
      return '?';
    }

    return fullName
      .split(' ')
      .filter(Boolean)
      .map((name: string) => name[0])
      .slice(0, 2)
      .join('')
      .toUpperCase();
  }
}
