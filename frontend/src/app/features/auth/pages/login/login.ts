import { Component, computed, inject, OnInit, signal } from '@angular/core';
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

  private auth = inject(AuthService);
  _isValid = signal(true);

  sessionExpired = computed(() => this.auth.logoutReason() === 'expired');

  loginForm = this.fb.group({
    email: [''],
    password: [''],
  });

  onSubmit(): void {
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

        if (err.status === 401) {
          this._isValid.set(false);
        }
      },
    });
  }
}
