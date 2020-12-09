import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { ShipmentTypeService } from './shipment-type.service';

@Component({
  templateUrl: './shipment-type-delete-dialog.component.html',
})
export class ShipmentTypeDeleteDialogComponent {
  shipmentType?: IShipmentType;

  constructor(
    protected shipmentTypeService: ShipmentTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.shipmentTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('shipmentTypeListModification');
      this.activeModal.close();
    });
  }
}
