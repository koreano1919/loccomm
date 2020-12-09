import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LoccommGwSharedModule } from 'app/shared/shared.module';
import { ProductAttrsComponent } from './product-attrs.component';
import { ProductAttrsDetailComponent } from './product-attrs-detail.component';
import { ProductAttrsUpdateComponent } from './product-attrs-update.component';
import { ProductAttrsDeleteDialogComponent } from './product-attrs-delete-dialog.component';
import { productAttrsRoute } from './product-attrs.route';

@NgModule({
  imports: [LoccommGwSharedModule, RouterModule.forChild(productAttrsRoute)],
  declarations: [ProductAttrsComponent, ProductAttrsDetailComponent, ProductAttrsUpdateComponent, ProductAttrsDeleteDialogComponent],
  entryComponents: [ProductAttrsDeleteDialogComponent],
})
export class LoccommGwProductAttrsModule {}
