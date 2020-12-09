import { ICartItem } from 'app/shared/model/cart-item.model';

export interface ICart {
  id?: number;
  amount?: number;
  cartItems?: ICartItem[];
}

export class Cart implements ICart {
  constructor(public id?: number, public amount?: number, public cartItems?: ICartItem[]) {}
}
