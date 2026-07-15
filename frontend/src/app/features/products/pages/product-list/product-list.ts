import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { PaginationComponent } from '../../../../shared/components/pagination/pagination';
import { RouterLink } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { ConfirmationModal } from '../../../../shared/components/confirmation-modal/confirmation-modal';
import { Pagination } from '../../../../shared/models/pagination.model';
import { debounceTime, distinctUntilChanged, Subject, Subscription } from 'rxjs';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { Product } from '../../models/product.model';
import { ProductResponse } from '../../models/product-response.model';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../../categories/services/category.service';
import { Category } from '../../../categories/models/category.model';

@Component({
  selector: 'app-product-list',
  imports: [PaginationComponent, RouterLink, DatePipe, ConfirmationModal, CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements OnInit, OnDestroy {
  private readonly productService = inject(ProductService);
  private flashMessageService = inject(FlashMessageService);
  private categoryService = inject(CategoryService);

  private categorySubscription: Subscription | null = null;
  private productSubscription: Subscription | null = null;

  private readonly searchSubject = new Subject<string>();

  readonly showDeleteModal = signal(false);
  readonly selectProduct = signal<ProductResponse | null>(null);

  _products = signal<Array<Product>>([]);
  _pagination = signal<Pagination | null>(null);
  _categories = signal<Array<Category>>([]);
  _search = signal('');
  _category = signal('');
  _price = signal('');
  _stock = signal('');

  successMessage = this.flashMessageService.message;

  ngOnInit(): void {
    //This is debounce to avoid request every typing
    this.searchSubject.pipe(debounceTime(300), distinctUntilChanged()).subscribe((value) => {
      this._search.set(value);
      this.loadProducts(0, value);
    });
    this.loadProducts(0);

    this.categorySubscription = this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        const content = response.content;
        this._categories.set(content);
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  ngOnDestroy(): void {
    if (this.categorySubscription) {
      this.categorySubscription.unsubscribe();
    }

    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
  }

  loadProducts(
    page: number = 0,
    search: string = this._search(),
    category: string = this._category(),
    price: string = this._price(),
    stock: string = this._stock(),
  ): void {
    this.productService.getProducts(page, search, category, price, stock).subscribe({
      next: (response) => {
        console.log(response);
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

  onCategoryChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;

    this._category.set(value);
    this.loadProducts(0);
  }

  onPriceChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;

    this._price.set(value);
    this.loadProducts(0);
  }

  onStockChange(event: Event): void {
    const value = (event.target as HTMLSelectElement).value;

    this._stock.set(value);
    this.loadProducts(0);
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
