import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { ProductFormService } from '../../services/product-form.service';
import { ProductRequest } from '../../models/product-request.mode';
import { ProductService } from '../../services/product.service';
import { SuccessResponse } from '../../../../shared/models/success-response.model';

@Component({
  selector: 'app-product-create',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './product-create.html',
  styleUrl: './product-create.css',
})
export class ProductCreate {
  private fb = inject(FormBuilder);
  private productService = inject(ProductService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);
  private productFormService = inject(ProductFormService);

  productForm = this.productFormService.createForm();

  onSubmit(): void {
    const request = this.productForm.getRawValue() as ProductRequest;

    this.productService.create(request).subscribe({
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
