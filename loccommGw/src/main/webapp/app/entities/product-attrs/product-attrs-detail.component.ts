import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductAttrs } from 'app/shared/model/product-attrs.model';

@Component({
  selector: 'jhi-product-attrs-detail',
  templateUrl: './product-attrs-detail.component.html',
})
export class ProductAttrsDetailComponent implements OnInit {
  productAttrs: IProductAttrs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAttrs }) => (this.productAttrs = productAttrs));
  }

  previousState(): void {
    window.history.back();
  }
}
