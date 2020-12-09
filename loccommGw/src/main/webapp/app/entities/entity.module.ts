import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.LoccommGwCategoryModule),
      },
      {
        path: 'store',
        loadChildren: () => import('./store/store.module').then(m => m.LoccommGwStoreModule),
      },
      {
        path: 'shipment',
        loadChildren: () => import('./shipment/shipment.module').then(m => m.LoccommGwShipmentModule),
      },
      {
        path: 'shipment-type',
        loadChildren: () => import('./shipment-type/shipment-type.module').then(m => m.LoccommGwShipmentTypeModule),
      },
      {
        path: 'cart',
        loadChildren: () => import('./cart/cart.module').then(m => m.LoccommGwCartModule),
      },
      {
        path: 'cart-item',
        loadChildren: () => import('./cart-item/cart-item.module').then(m => m.LoccommGwCartItemModule),
      },
      {
        path: 'order',
        loadChildren: () => import('./order/order.module').then(m => m.LoccommGwOrderModule),
      },
      {
        path: 'order-item',
        loadChildren: () => import('./order-item/order-item.module').then(m => m.LoccommGwOrderItemModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.LoccommGwCustomerModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.LoccommGwAddressModule),
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.LoccommGwProductModule),
      },
      {
        path: 'product-attribute',
        loadChildren: () => import('./product-attribute/product-attribute.module').then(m => m.LoccommGwProductAttributeModule),
      },
      {
        path: 'product-attrs',
        loadChildren: () => import('./product-attrs/product-attrs.module').then(m => m.LoccommGwProductAttrsModule),
      },
      {
        path: 'product-attribute-type',
        loadChildren: () =>
          import('./product-attribute-type/product-attribute-type.module').then(m => m.LoccommGwProductAttributeTypeModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class LoccommGwEntityModule {}
