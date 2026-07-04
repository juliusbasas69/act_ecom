import { Component, inject, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';

import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { Pagination } from '../../../../shared/models/pagination.model';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { ConfirmationModal } from '../../../../shared/components/confirmation-modal/confirmation-modal';
import { UserResponse } from '../../models/user-response.model';

@Component({
  selector: 'app-user-list',
  imports: [PaginationComponent, RouterLink, DatePipe, ConfirmationModal],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {
  private readonly userService = inject(UserService);
  private flashMessageService = inject(FlashMessageService);

  private readonly searchSubject = new Subject<string>();

  readonly showDeleteModal = signal(false);
  readonly selectedUser = signal<UserResponse | null>(null);

  _users = signal<Array<User>>([]);
  _pagination = signal<Pagination | null>(null);
  _search = signal('');
  successMessage = this.flashMessageService.message;

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this._search.set(value);
      this.loadUsers(0, value);
    });

    this.loadUsers(0);
  }

  loadUsers(page: number = 0, search: string = ''): void {
    this.userService.getUsers(page, search).subscribe({
      next: (response) => {
        console.log('Response:', response);
        console.log('Content:', response.content);

        this._users.set(response.content);
        this._pagination.set(response.pagination);
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

  openDeleteModal(user: UserResponse): void {
    this.selectedUser.set(user);
    this.showDeleteModal.set(true);
  }

  closeDeleteModal(): void {
    this.showDeleteModal.set(false);
    this.selectedUser.set(null);
  }

  confirmDelete(): void {
    const user = this.selectedUser();

    if (!user) {
      return;
    }

    this.userService.delete(user.encryptedId).subscribe({
      next: (response) => {
        console.log(response);
        this.flashMessageService.success(response.message);

        this.closeDeleteModal();

        this.loadUsers(this._pagination()?.page ?? 0);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
