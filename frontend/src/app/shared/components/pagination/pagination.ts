import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Pagination } from '../../models/pagination.model';

@Component({
  selector: 'app-pagination',
  imports: [],
  templateUrl: './pagination.html',
  styleUrl: './pagination.css',
})
export class PaginationComponent {
  @Input({ required: true }) pagination!: Pagination;

  @Input() label = 'users';

  @Output() pageChange = new EventEmitter<number>();

  previousPage(): void {
    if (this.pagination.hasPrevious) {
      this.pageChange.emit(this.pagination.page - 1);
    }
  }

  nextPage(): void {
    console.log('NEXT');
    if (this.pagination.hasNext) {
      this.pageChange.emit(this.pagination.page + 1);
    }
  }

  jumpToPage(value: string): void {
    const page = Number(value);

    if (Number.isNaN(page) || page < 1 || page > this.pagination.totalPages) {
      return;
    }

    this.pageChange.emit(page - 1); // Backend uses zero-based pages
  }
}
