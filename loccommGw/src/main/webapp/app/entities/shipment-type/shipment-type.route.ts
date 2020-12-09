import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IShipmentType, ShipmentType } from 'app/shared/model/shipment-type.model';
import { ShipmentTypeService } from './shipment-type.service';
import { ShipmentTypeComponent } from './shipment-type.component';
import { ShipmentTypeDetailComponent } from './shipment-type-detail.component';
import { ShipmentTypeUpdateComponent } from './shipment-type-update.component';

@Injectable({ providedIn: 'root' })
export class ShipmentTypeResolve implements Resolve<IShipmentType> {
  constructor(private service: ShipmentTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IShipmentType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((shipmentType: HttpResponse<ShipmentType>) => {
          if (shipmentType.body) {
            return of(shipmentType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ShipmentType());
  }
}

export const shipmentTypeRoute: Routes = [
  {
    path: '',
    component: ShipmentTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.shipmentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ShipmentTypeDetailComponent,
    resolve: {
      shipmentType: ShipmentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.shipmentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ShipmentTypeUpdateComponent,
    resolve: {
      shipmentType: ShipmentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.shipmentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ShipmentTypeUpdateComponent,
    resolve: {
      shipmentType: ShipmentTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.shipmentType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
