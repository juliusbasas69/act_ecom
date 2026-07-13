import { Component, inject, signal } from '@angular/core';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';
import { ConfirmationModal } from '../../../../shared/components/confirmation-modal/confirmation-modal';
import { CategoryService } from '../../services/category.service';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { CategoryResponse } from '../../models/category-response.model';
import { Category } from '../../models/category.model';
import { Pagination } from '../../../../shared/models/pagination.model';

@Component({
  selector: 'app-category-list',
  imports: [PaginationComponent, RouterLink, DatePipe, ConfirmationModal],
  templateUrl: './category-list.html',
  styleUrl: './category-list.css',
})
export class CategoryList {
  private readonly categoryService = inject(CategoryService);
  private flashMessageService = inject(FlashMessageService);

  private readonly searchSubject = new Subject<string>();

  readonly showDeleteModal = signal(false);
  readonly selectedCategory = signal<CategoryResponse | null>(null);

  _categories = signal<Array<Category>>([]);
  _pagination = signal<Pagination | null>(null);
  _search = signal('');
  successMessage = this.flashMessageService.message;

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this._search.set(value);
      this.loadCategories(0, value);
    });

    this.loadCategories(0);
  }

  loadCategories(page: number = 0, search: string = ''): void {
    this.categoryService.getCategories(page, search).subscribe({
      next: (response) => {
        console.log('Response:', response);
        console.log('Content:', response.content);

        this._categories.set(response.content);
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

  openDeleteModal(user: CategoryResponse): void {
    this.selectedCategory.set(user);
    this.showDeleteModal.set(true);
  }

  closeDeleteModal(): void {
    this.showDeleteModal.set(false);
    this.selectedCategory.set(null);
  }

  confirmDelete(): void {
    const category = this.selectedCategory();

    if (!category) {
      return;
    }

    this.categoryService.delete(category.encryptedId).subscribe({
      next: (response) => {
        console.log(response);
        this.flashMessageService.success(response.message);

        this.closeDeleteModal();

        this.loadCategories(this._pagination()?.page ?? 0);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }

  getTextColor(backgroundColor: string): string {
    if (!backgroundColor) {
      return '#ffffff';
    }

    const hex = backgroundColor.replace('#', '');

    const r = parseInt(hex.substring(0, 2), 16);
    const g = parseInt(hex.substring(2, 4), 16);
    const b = parseInt(hex.substring(4, 6), 16);

    // Perceived brightness
    const brightness = (r * 299 + g * 587 + b * 114) / 1000;

    return brightness > 186 ? '#000000' : '#ffffff';
  }
}
