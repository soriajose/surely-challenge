import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { Product } from '../../model/product.model';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent {
  @Input({ required: true }) product!: Product;
  @Input() loading = false;
  @Input() disableAdd = false;
  @Output() addToCart = new EventEmitter<number>();

  onAdd() {
    this.addToCart.emit(this.product.id);
  }
}
