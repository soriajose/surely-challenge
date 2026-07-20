import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { CartState } from '../../../../core/data-access/models/cart.model';

@Component({
  selector: 'app-cart-sidebar',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './cart-sidebar.component.html',
  styleUrls: ['./cart-sidebar.component.scss']
})
export class CartSidebarComponent {
  @Input() cart: CartState | null = null;
  @Input() loadingRemoveId: number | null = null;
  @Input() loadingCheckout = false;
  @Input() loadingDelete = false;
  @Input() readOnly = false;
  
  @Output() removeProduct = new EventEmitter<number>();
  @Output() checkoutCart = new EventEmitter<void>();
  @Output() deleteCart = new EventEmitter<void>();

  onRemove(productId: number) {
    this.removeProduct.emit(productId);
  }

  onCheckout() {
    this.checkoutCart.emit();
  }

  onDelete() {
    this.deleteCart.emit();
  }
}
