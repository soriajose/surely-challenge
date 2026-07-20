import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ProductCardComponent } from '../product-card/product-card.component';
import { Product } from '../../model/product.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [ProductCardComponent],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent {
  @Input({ required: true }) products: Product[] = [];
  @Input() loadingProducts: number[] = [];
  @Input() readOnly = false;
  @Output() addToCart = new EventEmitter<number>();

  onAdd(productId: number) {
    this.addToCart.emit(productId);
  }
}
