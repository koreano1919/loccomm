import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICartItem } from 'app/shared/model/cart-item.model';

type EntityResponseType = HttpResponse<ICartItem>;
type EntityArrayResponseType = HttpResponse<ICartItem[]>;

@Injectable({ providedIn: 'root' })
export class CartItemService {
  public resourceUrl = SERVER_API_URL + 'api/cart-items';

  constructor(protected http: HttpClient) {}

  create(cartItem: ICartItem): Observable<EntityResponseType> {
    return this.http.post<ICartItem>(this.resourceUrl, cartItem, { observe: 'response' });
  }

  update(cartItem: ICartItem): Observable<EntityResponseType> {
    return this.http.put<ICartItem>(this.resourceUrl, cartItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICartItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICartItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
