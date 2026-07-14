import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { CategoryService } from '../../../../categories/services/category.service';
import { Category } from '../../../../categories/models/category.model';
import { CommonModule } from '@angular/common';
import { map, Observable, of, Subscription } from 'rxjs';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList {
  // private categoryService = inject(CategoryService);
  // private categorySubscription: Subscription | null = null;

  // _categories = signal<Array<Category>>([]);

  // ngOnInit(): void {
  //   this.categorySubscription = this.categoryService.getAllCategories().subscribe({
  //     next: (response) => {
  //       const content = response.content;
  //       this._categories.set(content);
  //       console.log(this._categories());
  //     },
  //     error: (err) => {
  //       console.error(err);
  //     },
  //   });
  // }

  // ngOnDestroy(): void {
  //   if (this.categorySubscription) {
  //     this.categorySubscription.unsubscribe();
  //   }
  // }
  private categoryService = inject(CategoryService);

  categories$: Observable<Category[]> = of([]);

  constructor() {
    this.categories$ = this.categoryService.getAllCategories().pipe(
      // Map the PagedResponse<Category> to Category[]
      map((response) => response.content),
    );
  }
}
