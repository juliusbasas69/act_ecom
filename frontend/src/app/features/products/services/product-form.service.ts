import { Injectable } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ProductFormService {
  constructor(private fb: FormBuilder) {}

  createForm() {
    return this.fb.group({
      isFeatured: [false],
      productCode: [''],
      productName: [''],
      description: [''],
      category: [''],
      price: [0],
      quantity: [0],
      status: [''],
    });
  }
}
