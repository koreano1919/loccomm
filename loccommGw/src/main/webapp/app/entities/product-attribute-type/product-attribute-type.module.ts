import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LoccommGwSharedModule } from 'app/shared/shared.module';
import { ProductAttributeTypeComponent } from './product-attribute-type.component';
import { ProductAttributeTypeDetailComponent } from './product-attribute-type-detail.component';
import { ProductAttributeTypeUpdateComponent } from './product-attribute-type-update.component';
import { ProductAttributeTypeDeleteDialogComponent } from './product-attribute-type-delete-dialog.component';
import { productAttributeTypeRoute } from './product-attribute-type.route';

@NgModule({
  imports: [LoccommGwSharedModule, RouterModule.forChild(productAttributeTypeRoute)],
  declarations: [
    ProductAttributeTypeComponent,
    ProductAttributeTypeDetailComponent,
    ProductAttributeTypeUpdateComponent,
    ProductAttributeTypeDeleteDialogComponent,
  ],
  entryComponents: [ProductAttributeTypeDeleteDialogComponent],
})
export class LoccommGwProductAttributeTypeModule {}
