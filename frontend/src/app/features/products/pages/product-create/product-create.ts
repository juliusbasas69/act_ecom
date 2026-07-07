import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { UserService } from '../../../users/services/user.service';
import { Router, RouterLink } from '@angular/router';
import { FlashMessageService } from '../../../../shared/services/flash-message.service';
import { ProductFormService } from '../../../../shared/services/product-form.service';

@Component({
  selector: 'app-product-create',
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './product-create.html',
  styleUrl: './product-create.css',
})
export class ProductCreate {
  private fb = inject(FormBuilder);
  private userService = inject(UserService);
  private router = inject(Router);
  private flashMessageService = inject(FlashMessageService);
  private productFormService = inject(ProductFormService);

  productForm = this.productFormService.createForm();

  onSubmit(): void {}
}
