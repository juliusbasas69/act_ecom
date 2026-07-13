import { Component, inject } from '@angular/core';
import { CategoryFormService } from '../../services/category-form.service';
import { CategoryService } from '../../services/category.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { CategoryRequest } from '../../models/category-request.model';

@Component({
  selector: 'app-category-create',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './category-create.html',
  styleUrl: './category-create.css',
})
export class CategoryCreate {
  private categoryService = inject(CategoryService);
  private categoryFormService = inject(CategoryFormService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);

  categoryForm = this.categoryFormService.createForm();

  onSubmit(): void {
    const request = this.categoryForm.getRawValue() as CategoryRequest;

    console.log('Form Request:', request);

    this.categoryService.create(request).subscribe({
      next: (response: SuccessResponse) => {
        console.log(response.message);

        this.flashMessageService.success(response.message);
        this.router.navigate(['/admin/categories']);
      },
      error: (error) => {
        const errors = error.error as Record<string, string[]>;

        Object.entries(errors).forEach(([field, messages]) => {
          const control = this.categoryForm.get(field);

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
