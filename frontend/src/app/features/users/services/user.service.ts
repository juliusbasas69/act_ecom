import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../core/environment';
import { PagedResponse } from '../../../shared/models/page-response.model';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/users`;

  getUsers(page: number, search: string): Observable<PagedResponse<User>> {
    const token = localStorage.getItem('token');

    return this.http.get<PagedResponse<User>>(this.apiUrl, {
      headers: { Authorization: `Bearer ${token}` },
      params: { page: page.toString(), search: search },
    });
  }
}
