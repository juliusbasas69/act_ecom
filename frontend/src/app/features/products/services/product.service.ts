import { HttpClient } from '@angular/common/http';
import { environment } from '../../../core/environment';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SuccessResponse } from '../../../shared/models/success-response.model';
import { ProductRequest } from '../models/product-request.mode';
import { StorageUtil } from '../../../shared/utils/storage.utils';
import { ProductResponse } from '../models/product-response.model';
import { PagedResponse } from '../../../shared/models/page-response.model';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/products`;

  getProducts(page: number, search: string): Observable<PagedResponse<Product>> {
    const token = StorageUtil.getToken();

    return this.http.get<PagedResponse<Product>>(this.apiUrl, {
      headers: { Authorization: `Bearer ${token}` },
      params: { page: page.toString(), search: search },
    });
  }

  create(request: ProductRequest): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  findProductById(encryptedId: string): Observable<ProductResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;

    return this.http.get<ProductResponse>(api, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }

  edit(encryptedId: string | null, request: ProductRequest): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }

  delete(encryptedId: string | null): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/delete/${encryptedId}`;

    return this.http.post<SuccessResponse>(
      api,
      {},
      {
        headers: { Authorization: `Bearer ${token}` },
      },
    );
  }
}
