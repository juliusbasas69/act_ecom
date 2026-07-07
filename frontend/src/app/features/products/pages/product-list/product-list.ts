import { Component, inject, signal } from '@angular/core';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ConfirmationModal } from '../../../../shared/components/confirmation-modal/confirmation-modal';
import { Pagination } from '../../../../shared/models/pagination.model';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { UserService } from '../../../users/services/user.service';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { Product } from '../../models/product.model';
import { ProductResponse } from '../../models/product-response.model';

@Component({
  selector: 'app-product-list',
  imports: [PaginationComponent, RouterLink, DatePipe, ConfirmationModal],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList {
  private readonly userService = inject(UserService);
  private flashMessageService = inject(FlashMessageService);

  private readonly searchSubject = new Subject<string>();

  readonly showDeleteModal = signal(false);
  readonly selectedUser = signal<ProductResponse | null>(null);

  _users = signal<Array<Product>>([]);
  _pagination = signal<Pagination | null>(null);
  _search = signal('');
  successMessage = this.flashMessageService.message;

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this._search.set(value);
    });
  }

  onSearch(event: Event): void {
    const value = (event.target as HTMLInputElement).value;

    this.searchSubject.next(value);
  }
}
