export interface ICategory {
  id?: number;
}

export class Category implements ICategory {
  constructor(public id?: number) {}
}
