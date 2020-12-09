import { ICart } from 'app/shared/model/cart.model';

export interface ICartItem {
  id?: number;
  productId?: number;
  price?: number;
  cart?: ICart;
}

export class CartItem implements ICartItem {
  constructor(public id?: number, public productId?: number, public price?: number, public cart?: ICart) {}
}
