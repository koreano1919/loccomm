import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { ProductAttributeTypeService } from './product-attribute-type.service';
import { ProductAttributeTypeDeleteDialogComponent } from './product-attribute-type-delete-dialog.component';

@Component({
  selector: 'jhi-product-attribute-type',
  templateUrl: './product-attribute-type.component.html',
})
export class ProductAttributeTypeComponent implements OnInit, OnDestroy {
  productAttributeTypes?: IProductAttributeType[];
  eventSubscriber?: Subscription;

  constructor(
    protected productAttributeTypeService: ProductAttributeTypeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productAttributeTypeService
      .query()
      .subscribe((res: HttpResponse<IProductAttributeType[]>) => (this.productAttributeTypes = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductAttributeTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductAttributeType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductAttributeTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('productAttributeTypeListModification', () => this.loadAll());
  }

  delete(productAttributeType: IProductAttributeType): void {
    const modalRef = this.modalService.open(ProductAttributeTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productAttributeType = productAttributeType;
  }
}
