import { Component, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';

import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { Pagination } from '../../../../shared/models/pagination.model';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';

@Component({
  selector: 'app-user-list',
  imports: [PaginationComponent],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {
  private readonly userService = inject(UserService);

  private readonly searchSubject = new Subject<string>();

  users = signal<Array<User>>([]);
  pagination = signal<Pagination | null>(null);
  search = signal('');

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this.search.set(value);
      this.loadUsers(0, value);
    });

    this.loadUsers(0);
  }

  loadUsers(page: number = 0, search: string = ''): void {
    this.userService.getUsers(page, search).subscribe({
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

  onSearch(event: Event): void {
    const value = (event.target as HTMLInputElement).value;

    this.searchSubject.next(value);
  }
}
