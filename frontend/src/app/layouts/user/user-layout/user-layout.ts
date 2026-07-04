import { Component } from '@angular/core';
import { UserHeader } from '../components/user-header/user-header';
import { RouterOutlet } from '@angular/router';
import { UserSidebar } from '../components/user-sidebar/user-sidebar';

@Component({
  selector: 'app-user-layout',
  standalone: true,
  imports: [RouterOutlet, UserHeader, UserSidebar],
  templateUrl: './user-layout.html',
  styleUrl: './user-layout.css',
})
export class UserLayout {
  isSidebarOpen = false;

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
  }

  closeSidebar() {
    this.isSidebarOpen = false;
  }
}
