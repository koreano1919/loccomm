import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LoccommGwSharedModule } from 'app/shared/shared.module';
import { CartItemComponent } from './cart-item.component';
import { CartItemDetailComponent } from './cart-item-detail.component';
import { CartItemUpdateComponent } from './cart-item-update.component';
import { CartItemDeleteDialogComponent } from './cart-item-delete-dialog.component';
import { cartItemRoute } from './cart-item.route';

@NgModule({
  imports: [LoccommGwSharedModule, RouterModule.forChild(cartItemRoute)],
  declarations: [CartItemComponent, CartItemDetailComponent, CartItemUpdateComponent, CartItemDeleteDialogComponent],
  entryComponents: [CartItemDeleteDialogComponent],
})
export class LoccommGwCartItemModule {}
