import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IShipmentType } from 'app/shared/model/shipment-type.model';

type EntityResponseType = HttpResponse<IShipmentType>;
type EntityArrayResponseType = HttpResponse<IShipmentType[]>;

@Injectable({ providedIn: 'root' })
export class ShipmentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/shipment-types';

  constructor(protected http: HttpClient) {}

  create(shipmentType: IShipmentType): Observable<EntityResponseType> {
    return this.http.post<IShipmentType>(this.resourceUrl, shipmentType, { observe: 'response' });
  }

  update(shipmentType: IShipmentType): Observable<EntityResponseType> {
    return this.http.put<IShipmentType>(this.resourceUrl, shipmentType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IShipmentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IShipmentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
