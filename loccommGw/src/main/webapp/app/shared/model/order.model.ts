import { IOrderItem } from 'app/shared/model/order-item.model';

export interface IOrder {
  id?: number;
  orderId?: number;
  userId?: number;
  orderItems?: IOrderItem[];
}

export class Order implements IOrder {
  constructor(public id?: number, public orderId?: number, public userId?: number, public orderItems?: IOrderItem[]) {}
}
