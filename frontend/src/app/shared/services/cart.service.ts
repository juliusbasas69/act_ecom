import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../core/environment';
import { Observable } from 'rxjs';
import { SuccessResponse } from '../models/success-response.model';
import { StorageUtil } from '../utils/storage.utils';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/cart`;

  // addToCart(): Observable<SuccessResponse> {
  //   const token = StorageUtil.getToken();
  //   const api = `${this.apiUrl}/add`;

  //   return this.http.post(api, request, {
  //     Headers: {
  //       Authorization: `Bearer ${token}`,
  //     },
  //   });
  // }
}
