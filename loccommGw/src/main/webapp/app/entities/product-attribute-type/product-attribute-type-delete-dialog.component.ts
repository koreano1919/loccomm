import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { ProductAttributeTypeService } from './product-attribute-type.service';

@Component({
  templateUrl: './product-attribute-type-delete-dialog.component.html',
})
export class ProductAttributeTypeDeleteDialogComponent {
  productAttributeType?: IProductAttributeType;

  constructor(
    protected productAttributeTypeService: ProductAttributeTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productAttributeTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productAttributeTypeListModification');
      this.activeModal.close();
    });
  }
}
