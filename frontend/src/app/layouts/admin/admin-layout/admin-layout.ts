import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserHeader } from '../../user/components/user-header/user-header';
import { AdminHeader } from '../components/admin-header/admin-header';
import { AdminSidebar } from '../components/admin-sidebar/admin-sidebar';

@Component({
  selector: 'app-admin-layout',
  imports: [RouterOutlet, AdminHeader, AdminSidebar],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.css',
})
export class AdminLayout {
  isSidebarOpen = false;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  closeSidebar() {
    this.isSidebarOpen = false;
  }
}
