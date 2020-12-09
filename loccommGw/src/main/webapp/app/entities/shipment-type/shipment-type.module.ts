import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LoccommGwSharedModule } from 'app/shared/shared.module';
import { ShipmentTypeComponent } from './shipment-type.component';
import { ShipmentTypeDetailComponent } from './shipment-type-detail.component';
import { ShipmentTypeUpdateComponent } from './shipment-type-update.component';
import { ShipmentTypeDeleteDialogComponent } from './shipment-type-delete-dialog.component';
import { shipmentTypeRoute } from './shipment-type.route';

@NgModule({
  imports: [LoccommGwSharedModule, RouterModule.forChild(shipmentTypeRoute)],
  declarations: [ShipmentTypeComponent, ShipmentTypeDetailComponent, ShipmentTypeUpdateComponent, ShipmentTypeDeleteDialogComponent],
  entryComponents: [ShipmentTypeDeleteDialogComponent],
})
export class LoccommGwShipmentTypeModule {}
