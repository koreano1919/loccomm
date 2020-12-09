import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IShipmentType } from 'app/shared/model/shipment-type.model';
import { ShipmentTypeService } from './shipment-type.service';
import { ShipmentTypeDeleteDialogComponent } from './shipment-type-delete-dialog.component';

@Component({
  selector: 'jhi-shipment-type',
  templateUrl: './shipment-type.component.html',
})
export class ShipmentTypeComponent implements OnInit, OnDestroy {
  shipmentTypes?: IShipmentType[];
  eventSubscriber?: Subscription;

  constructor(
    protected shipmentTypeService: ShipmentTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.shipmentTypeService.query().subscribe((res: HttpResponse<IShipmentType[]>) => (this.shipmentTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInShipmentTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IShipmentType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInShipmentTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('shipmentTypeListModification', () => this.loadAll());
  }

  delete(shipmentType: IShipmentType): void {
    const modalRef = this.modalService.open(ShipmentTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.shipmentType = shipmentType;
  }
}
