import { computed, inject, Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

import { environment } from '../../core/environment';
import { AuthResponse, LoginRequest } from './auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/auth`;
  private readonly tokenKey = 'token';

  readonly currentUserToken = signal<string | null>(localStorage.getItem(this.tokenKey));

  readonly isLoggedIn = computed(() => this.currentUserToken() !== null);

  readonly role = computed(() => {
    const token = this.currentUserToken();

    if (!token) {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role as string | null;
    } catch {
      return null;
    }
  });

  login(request: LoginRequest): Observable<AuthResponse> {
    console.log('YAWA');
    return this.http
      .post<AuthResponse>(`${this.apiUrl}/login`, request)
      .pipe(tap((response) => this.handleAuthSuccess(response.token)));
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.currentUserToken.set(null);
  }

  private handleAuthSuccess(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.currentUserToken.set(token);

    console.log(this.role()); // Should print USER
    console.log(this.isLoggedIn()); // Should print true
  }
}
