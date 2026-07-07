import { HttpClient } from '@angular/common/http';
import { environment } from '../../../core/environment';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SuccessResponse } from '../../../shared/models/success-response.model';
import { ProductRequest } from '../models/product-request.mode';
import { StorageUtil } from '../../../shared/utils/storage.utils';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/products`;

  create(request: ProductRequest): Observable<SuccessResponse> {
    const token = StorageUtil.getToken();
    const api = `${this.apiUrl}/create`;

    return this.http.post<SuccessResponse>(api, request, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
