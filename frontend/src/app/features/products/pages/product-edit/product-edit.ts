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
import { FileService } from '../../../../shared/services/file.service';

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
  private fileService = inject(FileService);

  encryptedId: string | null = null;
  _product = signal<ProductResponse | null>(null);
  productForm = this.productFormService.createForm();
  _categories = signal<Array<Category>>([]);
  readonly imagePreview = signal<string | null>(null);
  selectedFile: File | null = null;

  ngOnInit(): void {
    this.encryptedId = this.route.snapshot.paramMap.get('encryptedId');

    if (!this.encryptedId) {
      return;
    }

    this.productService.findProductById(this.encryptedId).subscribe({
      next: (response) => {
        console.log(response);
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

    this.productService.findProductById(this.encryptedId).subscribe({
      next: (response) => {
        this.productForm.patchValue(response);

        if (response.imageName) {
          this.fileService.getProductImage(response.imageName).subscribe({
            next: (blob) => {
              this.imagePreview.set(URL.createObjectURL(blob));
            },
          });
        }
      },
    });
  }

  onSubmit(): void {
    const request = this.productForm.getRawValue() as ProductRequest;
    const image = this.selectedFile;

    this.productService.edit(this.encryptedId, request, image).subscribe({
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
