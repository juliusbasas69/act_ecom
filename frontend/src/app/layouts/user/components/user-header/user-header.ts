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
  private authService = inject(AuthService);
  private router = inject(Router);

  @Output() toggleSidebar = new EventEmitter<void>();

  onToggleSidebar() {
    this.toggleSidebar.emit();
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
