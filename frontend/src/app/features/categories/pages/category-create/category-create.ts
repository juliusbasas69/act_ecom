import { Component, inject } from '@angular/core';
import { CategoryFormService } from '../../services/category-form.service';
import { CategoryService } from '../../services/category.service';
import { ReactiveFormsModule } from '@angular/forms';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { CategoryRequest } from '../../models/category-request.model';
import { CATEGORY_ICONS } from '../../../../shared/utils/icons.util';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-create',
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './category-create.html',
  styleUrl: './category-create.css',
})
export class CategoryCreate {
  private categoryService = inject(CategoryService);
  private categoryFormService = inject(CategoryFormService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);

  categoryForm = this.categoryFormService.createForm();

  icons = CATEGORY_ICONS;
  search = '';

  onSubmit(): void {
    const request = this.categoryForm.getRawValue() as CategoryRequest;

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

  onSearch(event: Event): void {
    this.search = (event.target as HTMLInputElement).value.toLowerCase();
  }

  get filteredIcons() {
    return CATEGORY_ICONS.filter(
      (icon) =>
        icon.label.toLowerCase().includes(this.search.toLowerCase()) ||
        icon.value.toLowerCase().includes(this.search.toLowerCase()),
    );
  }
}
