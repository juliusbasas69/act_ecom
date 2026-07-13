import { Component, EventEmitter, inject, Output } from '@angular/core';
import { AuthService } from '../../../../features/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-header',
  imports: [],
  templateUrl: './user-header.html',
  styleUrl: './user-header.css',
})
export class UserHeader {
  readonly authService = inject(AuthService);
  private router = inject(Router);

  @Output() toggleSidebar = new EventEmitter<void>();

  onToggleSidebar() {
    this.toggleSidebar.emit();
  }

  onLogout() {
    this.authService.logout('manual');
    this.router.navigate(['/login']);
  }

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
