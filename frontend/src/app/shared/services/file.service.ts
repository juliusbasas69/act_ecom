import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../core/environment';
import { Observable } from 'rxjs';
import { StorageUtil } from '../utils/storage.utils';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  private http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/file`;

  getProductImage(imageName: string): Observable<Blob> {
    const token = StorageUtil.getToken();

    return this.http.get(`${this.apiUrl}/products/images/${imageName}`, {
      responseType: 'blob',
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
