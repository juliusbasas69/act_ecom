import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { ProductFormService } from '../../services/product-form.service';
import { ProductRequest } from '../../models/product-request.mode';
import { ProductService } from '../../services/product.service';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { CategoryService } from '../../../categories/services/category.service';
import { Category } from '../../../categories/models/category.model';

@Component({
  selector: 'app-product-create',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './product-create.html',
  styleUrl: './product-create.css',
})
export class ProductCreate implements OnInit {
  private productService = inject(ProductService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);
  private productFormService = inject(ProductFormService);
  private categoryService = inject(CategoryService);

  productForm = this.productFormService.createForm();

  _categories = signal<Array<Category>>([]);
  readonly imagePreview = signal<string | null>(null);
  selectedFile: File | null = null;

  ngOnInit(): void {
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
    const image = this.selectedFile;

    this.productService.create(request, image).subscribe({
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

  onImageSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (!input.files?.length) {
      return;
    }

    this.selectedFile = input.files[0];

    const reader = new FileReader();

    reader.onload = () => {
      this.imagePreview.set(reader.result as string);
    };

    reader.readAsDataURL(this.selectedFile);
  }
}
