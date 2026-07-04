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
  private readonly _logoutReason = signal<string | null>(null);
  readonly logoutReason = computed(() => this._logoutReason());

  readonly _currentUserToken = signal<string | null>(localStorage.getItem(this.tokenKey));

  readonly isLoggedIn = computed(() => {
    const token = this._currentUserToken();

    if (!token) return false;

    const expired = this.isTokenExpired();

    return !expired;
  });

  readonly role = computed(() => {
    const token = this._currentUserToken();

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

  logout(reason: string | null = null): void {
    localStorage.removeItem(this.tokenKey);
    this._currentUserToken.set(null);

    this._logoutReason.set(reason);
  }

  private handleAuthSuccess(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this._currentUserToken.set(token);

    console.log(this.role()); // Should print USER
    console.log(this.isLoggedIn()); // Should print true
  }

  isTokenExpired(): boolean {
    const token = this._currentUserToken();

    if (!token) return true;

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return Date.now() >= payload.exp * 1000;
    } catch {
      return true;
    }
  }
}
