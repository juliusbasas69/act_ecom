import { Component, ElementRef, inject, OnDestroy, OnInit, signal, ViewChild } from '@angular/core';
import { CategoryService } from '../../../../categories/services/category.service';
import { Category } from '../../../../categories/models/category.model';
import { CommonModule } from '@angular/common';
import { map, Observable, of, Subscription } from 'rxjs';
import { ProductService } from '../../../services/product.service';
import { Product } from '../../../models/product.model';

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
  private productService = inject(ProductService);

  categories$: Observable<Category[]> = of([]);
  products$: Observable<Array<Product>> = of([]);

  constructor() {
    this.categories$ = this.categoryService.getAllCategories().pipe(
      // Map the PagedResponse<Category> to Category[]
      map((response) => response.content),
    );

    this.products$ = this.productService.getFeaturedProducts().pipe(map((product) => product));
    console.log(this.products$);
    // this.products$.subscribe({
    //   next: (products) => {
    //     console.log('OKAY');
    //     console.log(products);
    //   },
    //   error: (error) => {
    //     console.error(error);
    //   },
    // });
  }

  @ViewChild('categoryContainer')
  categoryContainer!: ElementRef<HTMLDivElement>;

  scrollLeft() {
    this.categoryContainer.nativeElement.scrollBy({
      left: -300,
      behavior: 'smooth',
    });
  }

  scrollRight() {
    this.categoryContainer.nativeElement.scrollBy({
      left: 300,
      behavior: 'smooth',
    });
  }

  @ViewChild('productContainer')
  productContainer!: ElementRef<HTMLDivElement>;

  scrollProductsLeft() {
    this.productContainer.nativeElement.scrollBy({
      left: -900, // scroll about 3 cards
      behavior: 'smooth',
    });
  }

  scrollProductsRight() {
    this.productContainer.nativeElement.scrollBy({
      left: 900, // scroll about 3 cards
      behavior: 'smooth',
    });
  }
}
