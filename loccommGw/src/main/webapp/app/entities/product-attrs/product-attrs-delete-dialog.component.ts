import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttrs } from 'app/shared/model/product-attrs.model';
import { ProductAttrsService } from './product-attrs.service';

@Component({
  templateUrl: './product-attrs-delete-dialog.component.html',
})
export class ProductAttrsDeleteDialogComponent {
  productAttrs?: IProductAttrs;

  constructor(
    protected productAttrsService: ProductAttrsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productAttrsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productAttrsListModification');
      this.activeModal.close();
    });
  }
}
