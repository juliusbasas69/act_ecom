import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../core/environment';
import { Observable } from 'rxjs';
import { PagedResponse } from '../../../shared/models/page-response.model';
import { Category } from '../models/category.model';
import { StorageUtil } from '../../../shared/utils/storage.utils';
import { SuccessResponse } from '../../../shared/models/success-response.model';
import { CategoryRequest } from '../models/category-request.model';
import { CategoryResponse } from '../models/category-response.model';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/categories`;

  getCategories(page: number, search: string): Observable<PagedResponse<Category>> {
    const token = StorageUtil.getToken();

    return this.http.get<PagedResponse<Category>>(this.apiUrl, {
      headers: { Authorization: `Bearer ${token}` },
      params: { page: page.toString(), search: search },
    });
  }

  getAllCategories(): Observable<PagedResponse<Category>> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/all`;

    return this.http.get<PagedResponse<Category>>(api, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  create(request: CategoryRequest): Observable<SuccessResponse> {
    console.log(request);
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    console.log(api);

    return this.http.post<SuccessResponse>(api, request, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  findCategoryById(encryptedId: string): Observable<CategoryResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;

    return this.http.get<CategoryResponse>(api, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  edit(encryptedId: string | null, request: CategoryRequest): Observable<SuccessResponse> {
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
