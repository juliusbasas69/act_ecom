import { Component, inject } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { SuccessResponse } from '../../../../shared/models/success-response.model';
import { UserCreateRequest } from '../../models/user-create.model';

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

  createForm = this.fb.group({
    firstName: [''],
    familyName: [''],
    email: [''],
    password: [''],
    confirmPassword: [''],
    role: [''],
  });

  onSubmit(): void {
    const request = this.createForm.getRawValue() as UserCreateRequest;
    console.log(request);
    this.userService.create(request).subscribe({
      next: (response: SuccessResponse) => {
        console.log(response.message);

        this.router.navigate(['/admin/users']);
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
}
