import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../core/environment';
import { PagedResponse } from '../../../shared/models/page-response.model';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { StorageUtil } from '../../../shared/utils/storage.utils';
import { SuccessResponse } from '../../../shared/models/success-response.model';
import { UserRequest } from '../models/user-request.model';
import { UserResponse } from '../models/user-response.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/users`;

  getUsers(page: number, search: string): Observable<PagedResponse<User>> {
    const token = StorageUtil.getToken();

    return this.http.get<PagedResponse<User>>(this.apiUrl, {
      headers: { Authorization: `Bearer ${token}` },
      params: { page: page.toString(), search: search },
    });
  }

  create(request: UserRequest): Observable<SuccessResponse> {
    console.log(request);
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  findUserById(encryptedId: string): Observable<UserResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;
    console.log(api);
    return this.http.get<UserResponse>(api, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  edit(encryptedId: string | null, request: UserRequest): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  delete(encryptedId: string | null): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/delete/${encryptedId}`;
    console.log('THIS IS API: ' + api);
    return this.http.post<SuccessResponse>(
      api,
      {},
      {
        headers: { Authorization: `Bearer ${token}` },
      },
    );
  }
}
