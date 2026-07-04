import { Component, inject, OnInit, signal } from '@angular/core';
import { UserRequest } from '../../models/user-request.model';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UserFormService } from '../../../../shared/services/user-form.service';
import { UserResponse } from '../../models/user-response.model';

@Component({
  selector: 'app-user-edit',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './user-edit.html',
  styleUrl: './user-edit.css',
})
export class UserEdit implements OnInit {
  private userService = inject(UserService);
  private router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private flashMessageService = inject(FlashMessageService);
  private userFormService = inject(UserFormService);

  encryptedId: string | null = null;
  _user = signal<UserResponse | null>(null);
  userForm = this.userFormService.createForm();

  ngOnInit(): void {
    this.encryptedId = this.route.snapshot.paramMap.get('encryptedId');

    if (!this.encryptedId) {
      return;
    }

    this.userService.findUserById(this.encryptedId).subscribe({
      next: (response) => {
        console.log(response);
        this.userForm.patchValue(response);
      },
    });
  }

  onSubmit(): void {
    const request = this.userForm.getRawValue() as UserRequest;

    if (request.password === '') {
      request.password = null;
    }

    if (request.confirmPassword === '') {
      request.confirmPassword = null;
    }

    this.userService.edit(this.encryptedId, request).subscribe({
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
