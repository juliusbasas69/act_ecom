import { Component, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';

import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { Pagination } from '../../../../shared/models/pagination.model';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';

@Component({
  selector: 'app-user-list',
  imports: [PaginationComponent],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {
  private readonly userService = inject(UserService);

  users = signal<Array<User>>([]);
  pagination = signal<Pagination | null>(null);

  ngOnInit(): void {
    this.loadUsers(0);
  }

  loadUsers(page: number = 0): void {
    this.userService.getUsers(page).subscribe({
      next: (response) => {
        console.log('Response:', response);
        console.log('Content:', response.content);

        this.users.set(response.content);
        this.pagination.set(response.pagination);
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
