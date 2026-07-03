import { Component, EventEmitter, inject, Output } from '@angular/core';
import { AuthService } from '../../../../features/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-header',
  imports: [],
  templateUrl: './admin-header.html',
  styleUrl: './admin-header.css',
})
export class AdminHeader {
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
