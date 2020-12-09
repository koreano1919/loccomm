import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductAttributeType, ProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { ProductAttributeTypeService } from './product-attribute-type.service';
import { ProductAttributeTypeComponent } from './product-attribute-type.component';
import { ProductAttributeTypeDetailComponent } from './product-attribute-type-detail.component';
import { ProductAttributeTypeUpdateComponent } from './product-attribute-type-update.component';

@Injectable({ providedIn: 'root' })
export class ProductAttributeTypeResolve implements Resolve<IProductAttributeType> {
  constructor(private service: ProductAttributeTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductAttributeType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productAttributeType: HttpResponse<ProductAttributeType>) => {
          if (productAttributeType.body) {
            return of(productAttributeType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductAttributeType());
  }
}

export const productAttributeTypeRoute: Routes = [
  {
    path: '',
    component: ProductAttributeTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.productAttributeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductAttributeTypeDetailComponent,
    resolve: {
      productAttributeType: ProductAttributeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.productAttributeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductAttributeTypeUpdateComponent,
    resolve: {
      productAttributeType: ProductAttributeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.productAttributeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductAttributeTypeUpdateComponent,
    resolve: {
      productAttributeType: ProductAttributeTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'loccommGwApp.productAttributeType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
