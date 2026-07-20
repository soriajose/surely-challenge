import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-cart-lookup-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cart-lookup-form.component.html',
  styleUrl: './cart-lookup-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CartLookupFormComponent {
  @Input() loading = false;
  @Input() errorMessage: string | null = null;
  @Output() lookup = new EventEmitter<number>();

  readonly form = new FormGroup({
    cartId: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.pattern(/^\d+$/)]
    })
  });

  get cartIdInvalid(): boolean {
    const control = this.form.controls.cartId;
    return control.invalid && control.touched;
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.lookup.emit(Number(this.form.controls.cartId.value));
  }
}
