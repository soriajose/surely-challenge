import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { ProductService } from '../../../products/service/product.service';
import { Product } from '../../../products/model/product.model';
import { ProductCardComponent } from '../../../products/components/product-card/product-card.component';
import { ApiError } from '../../../../core/data-access/models/api-error.model';

@Component({
  selector: 'app-top-products-page',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule, ProductCardComponent],
  templateUrl: './top-products-page.component.html',
  styleUrls: ['./top-products-page.component.scss']
})
export class TopProductsPageComponent {
  private productService = inject(ProductService);

  readonly form = new FormGroup({
    documentNumber: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.minLength(6)]
    })
  });

  products = signal<Product[] | null>(null);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  get documentInvalid(): boolean {
    const control = this.form.controls.documentNumber;
    return control.invalid && control.touched;
  }

  onSubmit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);
    this.products.set(null);

    const doc = this.form.controls.documentNumber.value;
    
    this.productService.getTopExpensiveProducts(doc).subscribe({
      next: (data) => {
        this.products.set(data);
        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error?.message || 'No se pudieron cargar los productos.');
        this.loading.set(false);
      }
    });
  }
}
