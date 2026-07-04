import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthResponse, LoginRequest } from '../../auth.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  loginForm = this.fb.group({
    email: [''],
    password: [''],
  });

  onSubmit() {
    const request: LoginRequest = this.loginForm.getRawValue();
    console.log(request);

    this.authService.login(request).subscribe({
      next: (response: AuthResponse) => {
        console.log('This is role: ' + response.role);
        switch (response.role) {
          case 'ADMIN':
            this.router.navigate(['/admin/dashboard']);
            break;

          case 'USER':
            console.log('REDIRECTING TO USER');
            this.router.navigate(['/dashboard']);
            break;

          default:
            this.router.navigate(['/']);
        }
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);

        console.log(err.status); // HTTP status (e.g. 401)
        console.log(err.message); // Error message
        console.log(err.error); // Response body from your backend
      },
    });
  }
}
