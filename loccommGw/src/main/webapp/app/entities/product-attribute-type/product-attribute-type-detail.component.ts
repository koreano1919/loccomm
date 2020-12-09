import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';

@Component({
  selector: 'jhi-product-attribute-type-detail',
  templateUrl: './product-attribute-type-detail.component.html',
})
export class ProductAttributeTypeDetailComponent implements OnInit {
  productAttributeType: IProductAttributeType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAttributeType }) => (this.productAttributeType = productAttributeType));
  }

  previousState(): void {
    window.history.back();
  }
}
