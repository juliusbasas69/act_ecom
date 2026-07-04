import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class FlashMessageService {
  private readonly _message = signal<string | null>(null);

  readonly message = this._message.asReadonly();

  success(message: string): void {
    this._message.set(message);
  }

  clear(): void {
    this._message.set(null);
  }
}
