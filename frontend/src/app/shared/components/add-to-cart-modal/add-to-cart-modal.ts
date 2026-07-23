import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ProductResponse } from '../../../features/products/models/product-response.model';
import { CommonModule } from '@angular/common';
import { Product } from '../../../features/products/models/product.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-add-to-cart-modal',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-to-cart-modal.html',
  styleUrl: './add-to-cart-modal.css',
})
export class AddToCartModal {
  @Input() visible = false;
  @Input() product!: Product;

  @Output() confirm = new EventEmitter<number>();
  @Output() cancel = new EventEmitter<void>();

  quantity = 1;

  increaseQuantity(): void {
    if (this.quantity < this.product.quantity) {
      this.quantity++;
    }
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  onConfirm(): void {
    this.confirm.emit(this.quantity);
  }

  onCancel(): void {
    this.quantity = 1;
    this.cancel.emit();
  }
}
