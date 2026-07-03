import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-admin-sidebar',
  imports: [],
  templateUrl: './admin-sidebar.html',
  styleUrl: './admin-sidebar.css',
})
export class AdminSidebar {
  @Input() isSidebarOpen = false;

  @Output() close = new EventEmitter<void>();

  onClose() {
    this.close.emit();
  }
}
