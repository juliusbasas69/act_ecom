import { Injectable } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class CategoryFormService {
  constructor(private fb: FormBuilder) {}

  createForm() {
    return this.fb.group({
      categoryCode: [''],
      categoryName: [''],
      description: [''],
      color: [''],
      status: [''],
    });
  }
}
