import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ProductListComponent } from '../../components/product-list/product-list.component';
import { CartSidebarComponent } from '../../components/cart-sidebar/cart-sidebar.component';
import { ProductService } from '../../service/product.service';
import { CartService } from '../../../../core/data-access/cart.service';
import { Product } from '../../model/product.model';
import { CartState } from '../../../../core/data-access/models/cart.model';
import { HttpErrorResponse } from '@angular/common/http';
import { ApiError } from '../../../../core/data-access/models/api-error.model';

@Component({
  selector: 'app-store-page',
  standalone: true,
  imports: [ProductListComponent, CartSidebarComponent, RouterLink],
  templateUrl: './store-page.component.html',
  styleUrls: ['./store-page.component.scss']
})
export class StorePageComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private productService = inject(ProductService);
  private cartService = inject(CartService);

  cartId = signal<number>(0);
  products = signal<Product[]>([]);
  cartState = signal<CartState | null>(null);

  // Compute readOnly based on cart status
  readonly readOnly = computed(() => {
    const state = this.cartState();
    return state ? state.status !== 'OPEN' : false;
  });

  loadingProducts = signal<number[]>([]);
  loadingRemoveId = signal<number | null>(null);
  loadingCheckout = signal<boolean>(false);
  loadingDeleteCart = signal<boolean>(false);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('cartId');
    if (idParam) {
      this.cartId.set(Number(idParam));
      this.loadCatalog();
      this.loadCart();
    } else {
      this.router.navigate(['/']);
    }
  }

  loadCatalog() {
    this.productService.getAllProducts().subscribe({
      next: (data) => this.products.set(data),
      error: () => this.errorMessage.set('Error al cargar el catálogo de productos.')
    });
  }

  loadCart() {
    this.cartService.getCartState(this.cartId()).subscribe({
      next: (state) => this.cartState.set(state),
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error.message || 'Error al cargar el carrito.');
      }
    });
  }

  onAddToCart(productId: number) {
    this.loadingProducts.update(curr => [...curr, productId]);
    this.errorMessage.set(null);

    this.cartService.addProduct(this.cartId(), productId).subscribe({
      next: () => {
        this.loadCart();
        this.loadingProducts.update(curr => curr.filter(id => id !== productId));
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error.message || 'No se pudo agregar el producto.');
        this.loadingProducts.update(curr => curr.filter(id => id !== productId));
      }
    });
  }

  onRemoveProduct(productId: number) {
    this.loadingRemoveId.set(productId);
    this.errorMessage.set(null);

    this.cartService.removeProduct(this.cartId(), productId).subscribe({
      next: () => {
        this.loadCart();
        this.loadingRemoveId.set(null);
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error.message || 'No se pudo eliminar el producto.');
        this.loadingRemoveId.set(null);
      }
    });
  }

  showCheckoutModal = signal<boolean>(false);
  checkoutSuccess = signal<boolean>(false);

  onCheckout() {
    this.showCheckoutModal.set(true);
  }

  cancelCheckout() {
    this.showCheckoutModal.set(false);
  }

  confirmCheckout() {
    this.loadingCheckout.set(true);
    this.errorMessage.set(null);

    this.cartService.checkoutCart(this.cartId()).subscribe({
      next: () => {
        this.loadingCheckout.set(false);
        this.checkoutSuccess.set(true);
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error.message || 'Error al finalizar la compra.');
        this.loadingCheckout.set(false);
        this.showCheckoutModal.set(false);
      }
    });
  }

  closeCheckoutSuccess() {
    this.checkoutSuccess.set(false);
    this.showCheckoutModal.set(false);
    this.router.navigate(['/']);
  }

  showDeleteModal = signal<boolean>(false);

  onDeleteCart() {
    this.showDeleteModal.set(true);
  }

  cancelDeleteCart() {
    this.showDeleteModal.set(false);
  }

  confirmDeleteCart() {
    this.loadingDeleteCart.set(true);
    this.errorMessage.set(null);

    this.cartService.deleteCart(this.cartId()).subscribe({
      next: () => {
        this.loadingDeleteCart.set(false);
        this.showDeleteModal.set(false);
        this.router.navigate(['/']);
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ApiError;
        this.errorMessage.set(error.message || 'Error al eliminar el carrito.');
        this.loadingDeleteCart.set(false);
        this.showDeleteModal.set(false);
      }
    });
  }
}
