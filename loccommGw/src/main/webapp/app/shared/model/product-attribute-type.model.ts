import { IProductAttribute } from 'app/shared/model/product-attribute.model';

export interface IProductAttributeType {
  id?: number;
  name?: string;
  value?: string;
  description?: string;
  productAttribute?: IProductAttribute;
}

export class ProductAttributeType implements IProductAttributeType {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public description?: string,
    public productAttribute?: IProductAttribute
  ) {}
}
