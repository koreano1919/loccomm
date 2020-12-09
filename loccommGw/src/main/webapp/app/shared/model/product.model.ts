import { IProductAttribute } from 'app/shared/model/product-attribute.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  productAttributes?: IProductAttribute[];
}

export class Product implements IProduct {
  constructor(public id?: number, public name?: string, public price?: number, public productAttributes?: IProductAttribute[]) {}
}
