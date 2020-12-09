import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductAttrs } from 'app/shared/model/product-attrs.model';
import { ProductAttrsService } from './product-attrs.service';
import { ProductAttrsDeleteDialogComponent } from './product-attrs-delete-dialog.component';

@Component({
  selector: 'jhi-product-attrs',
  templateUrl: './product-attrs.component.html',
})
export class ProductAttrsComponent implements OnInit, OnDestroy {
  productAttrs?: IProductAttrs[];
  eventSubscriber?: Subscription;

  constructor(
    protected productAttrsService: ProductAttrsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productAttrsService.query().subscribe((res: HttpResponse<IProductAttrs[]>) => (this.productAttrs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductAttrs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductAttrs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductAttrs(): void {
    this.eventSubscriber = this.eventManager.subscribe('productAttrsListModification', () => this.loadAll());
  }

  delete(productAttrs: IProductAttrs): void {
    const modalRef = this.modalService.open(ProductAttrsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productAttrs = productAttrs;
  }
}
