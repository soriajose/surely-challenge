export interface CartItem {
  productId: number;
  productName: string;
  quantity: number;
  subtotal: number;
}

/** Response shape returned by POST /cart */
export interface CreateCartResponse {
  cartId: number;
  message: string;
}

/** Response shape returned by GET /cart/{cartId} */
export interface CartState {
  cartId: number;
  status: string;
  total: number;
  items: CartItem[];
  message: string;
}

/** Response shape returned by POST /cart/{cartId}/product and DELETE /cart/{cartId}/product/{productId} */
export interface CartActionResult {
  cartId: number;
  message: string;
  totalAmount: number;
  items: CartItem[];
}
