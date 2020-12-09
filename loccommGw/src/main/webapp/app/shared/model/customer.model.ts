import { IAddress } from 'app/shared/model/address.model';

export interface ICustomer {
  id?: number;
  name?: string;
  surname?: string;
  addresses?: IAddress[];
}

export class Customer implements ICustomer {
  constructor(public id?: number, public name?: string, public surname?: string, public addresses?: IAddress[]) {}
}
