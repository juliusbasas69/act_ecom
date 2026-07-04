import { Injectable } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class UserFormService {
  constructor(private fb: FormBuilder) {}

  createForm() {
    return this.fb.group({
      firstName: [''],
      familyName: [''],
      email: [''],
      password: [''],
      confirmPassword: [''],
      role: [''],
    });
  }
}
