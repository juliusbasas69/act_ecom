import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-user-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './user-sidebar.html',
  styleUrls: ['./user-sidebar.css'],
})
export class UserSidebar {
  @Input() isSidebarOpen = false;

  @Output() close = new EventEmitter<void>();

  onClose() {
    this.close.emit();
  }
}
