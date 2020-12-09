import { IOrder } from 'app/shared/model/order.model';

export interface IOrderItem {
  id?: number;
  orderId?: number;
  productId?: number;
  order?: IOrder;
}

export class OrderItem implements IOrderItem {
  constructor(public id?: number, public orderId?: number, public productId?: number, public order?: IOrder) {}
}
