import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';

type EntityResponseType = HttpResponse<IProductAttributeType>;
type EntityArrayResponseType = HttpResponse<IProductAttributeType[]>;

@Injectable({ providedIn: 'root' })
export class ProductAttributeTypeService {
  public resourceUrl = SERVER_API_URL + 'api/product-attribute-types';

  constructor(protected http: HttpClient) {}

  create(productAttributeType: IProductAttributeType): Observable<EntityResponseType> {
    return this.http.post<IProductAttributeType>(this.resourceUrl, productAttributeType, { observe: 'response' });
  }

  update(productAttributeType: IProductAttributeType): Observable<EntityResponseType> {
    return this.http.put<IProductAttributeType>(this.resourceUrl, productAttributeType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductAttributeType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductAttributeType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
