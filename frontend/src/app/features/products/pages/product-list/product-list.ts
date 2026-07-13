import { Component, inject, signal } from '@angular/core';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { RouterLink } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { ConfirmationModal } from '../../../../shared/components/confirmation-modal/confirmation-modal';
import { Pagination } from '../../../../shared/models/pagination.model';
import { debounceTime, distinctUntilChanged, Subject } from 'rxjs';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { Product } from '../../models/product.model';
import { ProductResponse } from '../../models/product-response.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-list',
  imports: [PaginationComponent, RouterLink, DatePipe, ConfirmationModal, CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList {
  private readonly productService = inject(ProductService);
  private flashMessageService = inject(FlashMessageService);

  private readonly searchSubject = new Subject<string>();

  readonly showDeleteModal = signal(false);
  readonly selectProduct = signal<ProductResponse | null>(null);

  _products = signal<Array<Product>>([]);
  _pagination = signal<Pagination | null>(null);
  _search = signal('');
  successMessage = this.flashMessageService.message;

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this._search.set(value);
      this.loadProducts(0, value);
    });
    this.loadProducts(0);
  }

  loadProducts(page: number = 0, search: string = ''): void {
    this.productService.getProducts(page, search).subscribe({
      next: (response) => {
        this._products.set(response.content);
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

  openDeleteModal(product: ProductResponse): void {
    this.selectProduct.set(product);
    this.showDeleteModal.set(true);
  }

  closeDeleteModal(): void {
    this.showDeleteModal.set(false);
    this.selectProduct.set(null);
  }

  confirmDelete(): void {
    const product = this.selectProduct();

    if (!product) {
      return;
    }

    this.productService.delete(product.encryptedId).subscribe({
      next: (response) => {
        this.flashMessageService.success(response.message);

        this.closeDeleteModal();

        this.loadProducts(this._pagination()?.page ?? 0);
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
