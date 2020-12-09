export interface IShipment {
  id?: number;
}

export class Shipment implements IShipment {
  constructor(public id?: number) {}
}
