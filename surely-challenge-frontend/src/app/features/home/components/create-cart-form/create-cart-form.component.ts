import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-cart-form',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './create-cart-form.component.html',
  styleUrl: './create-cart-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateCartFormComponent {
  @Input() loading = false;
  @Input() errorMessage: string | null = null;
  @Output() create = new EventEmitter<string>();

  readonly form = new FormGroup({
    documentNumber: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.pattern(/^\d{6,10}$/)]
    })
  });

  get documentNumberInvalid(): boolean {
    const control = this.form.controls.documentNumber;
    return control.invalid && control.touched;
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.create.emit(this.form.controls.documentNumber.value.trim());
  }
}
