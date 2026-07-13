import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { ProductFormService } from '../../services/product-form.service';
import { ProductRequest } from '../../models/product-request.mode';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { ProductResponse } from '../../models/product-response.model';
import { CategoryService } from '../../../categories/services/category.service';
import { Category } from '../../../categories/models/category.model';

@Component({
  selector: 'app-product-edit',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './product-edit.html',
  styleUrl: './product-edit.css',
})
export class ProductEdit implements OnInit {
  private productService = inject(ProductService);
  private router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private flashMessageService = inject(FlashMessageService);
  private productFormService = inject(ProductFormService);
  private categoryService = inject(CategoryService);

  encryptedId: string | null = null;
  _product = signal<ProductResponse | null>(null);
  productForm = this.productFormService.createForm();
  _categories = signal<Array<Category>>([]);

  ngOnInit(): void {
    this.encryptedId = this.route.snapshot.paramMap.get('encryptedId');

    if (!this.encryptedId) {
      return;
    }

    this.productService.findProductById(this.encryptedId).subscribe({
      next: (response) => {
        this.productForm.patchValue(response);
      },
    });

    this.categoryService.getAllCategories().subscribe({
      next: (response) => {
        const categories = response.content;
        this._categories.set(categories);
        console.log('Fetched categories:', categories);
      },
      error: (error) => {
        console.error('Error fetching categories:', error);
      },
    });
  }

  onSubmit(): void {
    const request = this.productForm.getRawValue() as ProductRequest;

    this.productService.edit(this.encryptedId, request).subscribe({
      next: (response: SuccessResponse) => {
        this.flashMessageService.success(response.message);
        this.router.navigate(['/admin/products']);
      },
      error: (error) => {
        const errors = error.error as Record<string, string[]>;

        Object.entries(errors).forEach(([field, messages]) => {
          const control = this.productForm.get(field);

          if (control) {
            control.setErrors({
              server: messages,
            });
          }
        });
      },
    });
  }
}
