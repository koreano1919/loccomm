import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  address?: string;
  customer?: ICustomer;
}

export class Address implements IAddress {
  constructor(public id?: number, public address?: string, public customer?: ICustomer) {}
}
