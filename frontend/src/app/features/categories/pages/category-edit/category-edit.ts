import { Component, inject, OnInit, signal } from '@angular/core';
import { CategoryService } from '../../services/category.service';
import { CategoryFormService } from '../../services/category-form.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { CategoryRequest } from '../../models/category-request.model';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { CategoryResponse } from '../../models/category-response.model';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CATEGORY_ICONS } from '../../../../shared/utils/icons.util';

@Component({
  selector: 'app-category-edit',
  imports: [RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './category-edit.html',
  styleUrl: './category-edit.css',
})
export class CategoryEdit implements OnInit {
  private categoryService = inject(CategoryService);
  private categoryFormService = inject(CategoryFormService);
  private router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private flashMessageService = inject(FlashMessageService);

  encryptedId: string | null = null;
  _category = signal<CategoryResponse | null>(null);
  categoryForm = this.categoryFormService.createForm();

  icons = CATEGORY_ICONS;
  search = '';

  ngOnInit(): void {
    this.encryptedId = this.route.snapshot.paramMap.get('encryptedId');

    if (!this.encryptedId) {
      return;
    }

    this.categoryService.findCategoryById(this.encryptedId).subscribe({
      next: (response) => {
        console.log(response);
        this.categoryForm.patchValue(response);
      },
    });
  }

  onSubmit(): void {
    const request = this.categoryForm.getRawValue() as CategoryRequest;

    console.log('Form Request:', request);

    this.categoryService.edit(this.encryptedId, request).subscribe({
      next: (response: SuccessResponse) => {
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
