import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { CartService } from '../../../../core/data-access/cart.service';
import { ApiError } from '../../../../core/data-access/models/api-error.model';
import { CreateCartResponse } from '../../../../core/data-access/models/cart.model';
import { CartLookupFormComponent } from '../../components/cart-lookup-form/cart-lookup-form.component';
import { CreateCartFormComponent } from '../../components/create-cart-form/create-cart-form.component';

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [CreateCartFormComponent, CartLookupFormComponent, RouterLink],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomePageComponent {
  private readonly cartService = inject(CartService);
  private readonly router = inject(Router);

  readonly creating = signal(false);
  readonly createError = signal<string | null>(null);
  readonly createdCart = signal<CreateCartResponse | null>(null);

  onCreateCart(documentNumber: string): void {
    this.creating.set(true);
    this.createError.set(null);
    this.createdCart.set(null);

    this.cartService.createCart(documentNumber).subscribe({
      next: (response) => {
        this.creating.set(false);
        this.createdCart.set(response);
        this.router.navigate(['/store', response.cartId]);
      },
      error: (err: HttpErrorResponse) => {
        this.creating.set(false);
        const apiError = err.error as ApiError | undefined;
        this.createError.set(apiError?.message ?? 'No se pudo crear el carrito. Intentá nuevamente.');
      }
    });
  }

  readonly lookingUp = signal(false);
  readonly lookupError = signal<string | null>(null);

  onLookupCart(cartId: number): void {
    this.lookingUp.set(true);
    this.lookupError.set(null);
    
    this.cartService.getCartState(cartId).subscribe({
      next: () => {
        this.lookingUp.set(false);
        this.router.navigate(['/store', cartId]);
      },
      error: (err: HttpErrorResponse) => {
        this.lookingUp.set(false);
        const apiError = err.error as ApiError | undefined;
        this.lookupError.set(apiError?.message ?? 'El carrito no existe o ya fue eliminado.');
      }
    });
  }
}
