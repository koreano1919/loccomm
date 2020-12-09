export interface IStore {
  id?: number;
}

export class Store implements IStore {
  constructor(public id?: number) {}
}
