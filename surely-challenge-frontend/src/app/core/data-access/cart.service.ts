import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { CartActionResult, CartState, CreateCartResponse } from './models/cart.model';

@Injectable({ providedIn: 'root' })
export class CartService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/cart`;

  createCart(documentNumber: string): Observable<CreateCartResponse> {
    return this.http.post<CreateCartResponse>(this.baseUrl, { documentNumber });
  }

  getCartState(cartId: number): Observable<CartState> {
    return this.http.get<CartState>(`${this.baseUrl}/${cartId}`);
  }

  addProduct(cartId: number, productId: number): Observable<CartActionResult> {
    return this.http.post<CartActionResult>(`${this.baseUrl}/${cartId}/product`, { productId });
  }

  removeProduct(cartId: number, productId: number): Observable<CartActionResult> {
    return this.http.delete<CartActionResult>(`${this.baseUrl}/${cartId}/product/${productId}`);
  }

  deleteCart(cartId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${cartId}`);
  }

  checkoutCart(cartId: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/${cartId}/checkout`, {});
  }
}
