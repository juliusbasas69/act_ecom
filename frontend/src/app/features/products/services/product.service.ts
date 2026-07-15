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

  getProducts(
    page: number,
    search: string,
    category: string,
    price: string,
    stock: string,
  ): Observable<PagedResponse<Product>> {
    const token = StorageUtil.getToken();

    return this.http.get<PagedResponse<Product>>(this.apiUrl, {
      headers: { Authorization: `Bearer ${token}` },
      params: {
        page: page.toString(),
        search: search,
        category: category,
        price: price,
        stock: stock,
      },
    });
  }

  create(request: ProductRequest, image: File | null): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(request)], {
        type: 'application/json',
      }),
    );

    if (image) {
      formData.append('image', image);
    }

    return this.http.post<SuccessResponse>(api, formData, {
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

  edit(
    encryptedId: string | null,
    request: ProductRequest,
    image: File | null,
  ): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/edit/${encryptedId}`;

    const formData = new FormData();

    formData.append(
      'product',
      new Blob([JSON.stringify(request)], {
        type: 'application/json',
      }),
    );

    if (image) {
      formData.append('image', image);
    }

    return this.http.post<SuccessResponse>(api, formData, {
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
