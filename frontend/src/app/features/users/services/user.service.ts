import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../core/environment';
import { PagedResponse } from '../../../shared/models/page-response.model';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { StorageUtil } from '../../../shared/utils/storage.utils';
import { SuccessResponse } from '../../../shared/models/success-response.model';
import { UserCreateRequest } from '../models/user-create.model';

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

  create(request: UserCreateRequest): Observable<SuccessResponse> {
    console.log(request);
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }
}
