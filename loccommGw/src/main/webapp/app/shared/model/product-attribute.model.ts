import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IProductAttribute {
  id?: number;
  productId?: number;
  name?: string;
  value?: string;
  description?: string;
  type?: number;
  productAttributeType?: IProductAttributeType;
  product?: IProduct;
}

export class ProductAttribute implements IProductAttribute {
  constructor(
    public id?: number,
    public productId?: number,
    public name?: string,
    public value?: string,
    public description?: string,
    public type?: number,
    public productAttributeType?: IProductAttributeType,
    public product?: IProduct
  ) {}
}
