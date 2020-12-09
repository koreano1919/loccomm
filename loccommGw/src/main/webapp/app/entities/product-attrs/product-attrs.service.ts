import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductAttrs } from 'app/shared/model/product-attrs.model';

type EntityResponseType = HttpResponse<IProductAttrs>;
type EntityArrayResponseType = HttpResponse<IProductAttrs[]>;

@Injectable({ providedIn: 'root' })
export class ProductAttrsService {
  public resourceUrl = SERVER_API_URL + 'api/product-attrs';

  constructor(protected http: HttpClient) {}

  create(productAttrs: IProductAttrs): Observable<EntityResponseType> {
    return this.http.post<IProductAttrs>(this.resourceUrl, productAttrs, { observe: 'response' });
  }

  update(productAttrs: IProductAttrs): Observable<EntityResponseType> {
    return this.http.put<IProductAttrs>(this.resourceUrl, productAttrs, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductAttrs>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductAttrs[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
