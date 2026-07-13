import { Component, inject, OnInit, signal } from '@angular/core';
import { CategoryService } from '../../../../categories/services/category.service';
import { Category } from '../../../../categories/models/category.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements OnInit {
  private categoryService = inject(CategoryService);

  _categories = signal<Array<Category>>([]);

  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        const content = response.content;
        this._categories.set(content);
        console.log(this._categories());
      },
      error: (err) => {
        console.error(err);
      },
    });
  }
}
