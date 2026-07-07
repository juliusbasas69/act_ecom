import { Injectable } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ProductFormService {
  constructor(private fb: FormBuilder) {}

  createForm() {
    return this.fb.group({
      productCode: [''],
      productName: [''],
      description: [''],
      category: [''],
      price: [null],
      stockQuantity: [null],
      status: [],
    });
  }
}
