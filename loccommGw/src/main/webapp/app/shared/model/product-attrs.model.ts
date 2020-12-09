export interface IProductAttrs {
  id?: number;
  productId?: number;
  productAttributeId?: number;
}

export class ProductAttrs implements IProductAttrs {
  constructor(public id?: number, public productId?: number, public productAttributeId?: number) {}
}
