export interface IShipmentType {
  id?: number;
  name?: string;
  value?: string;
  description?: string;
}

export class ShipmentType implements IShipmentType {
  constructor(public id?: number, public name?: string, public value?: string, public description?: string) {}
}
