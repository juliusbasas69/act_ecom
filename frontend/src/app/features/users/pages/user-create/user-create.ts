import { Component, inject } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { UserRequest } from '../../models/user-request.model';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { UserFormService } from '../../../../shared/services/user-form.service';

@Component({
  selector: 'app-user-create',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './user-create.html',
  styleUrl: './user-create.css',
})
export class UserCreate {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);
  private userFormService = inject(UserFormService);

  userForm = this.userFormService.createForm();

  onSubmit(): void {
    const request = this.userForm.getRawValue() as UserRequest;
    console.log(request);
    this.userService.create(request).subscribe({
      next: (response: SuccessResponse) => {
        console.log(response.message);

        this.flashMessageService.success(response.message);
        this.router.navigate(['/admin/users']);
      },
      error: (error) => {
        const errors = error.error as Record<string, string[]>;

        Object.entries(errors).forEach(([field, messages]) => {
          const control = this.userForm.get(field);

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
