import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-confirmation-modal',
  imports: [],
  templateUrl: './confirmation-modal.html',
  styleUrl: './confirmation-modal.css',
})
export class ConfirmationModal {
  @Input() visible = false;
  @Input() title = 'Confirm';
  @Input() message = '';
  @Input() confirmText = 'CONFIRM';
  @Input() cancelText = 'CANCEL';
  @Input() confirmButtonClass = 'bg-red-600 hover:bg-red-700';

  @Output() confirm = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();

  onConfirm(): void {
    this.confirm.emit();
  }

  onCancel(): void {
    this.cancel.emit();
  }
}
