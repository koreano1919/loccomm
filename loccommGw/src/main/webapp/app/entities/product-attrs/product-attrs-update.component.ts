import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductAttrs, ProductAttrs } from 'app/shared/model/product-attrs.model';
import { ProductAttrsService } from './product-attrs.service';

@Component({
  selector: 'jhi-product-attrs-update',
  templateUrl: './product-attrs-update.component.html',
})
export class ProductAttrsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    productId: [],
    productAttributeId: [],
  });

  constructor(protected productAttrsService: ProductAttrsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAttrs }) => {
      this.updateForm(productAttrs);
    });
  }

  updateForm(productAttrs: IProductAttrs): void {
    this.editForm.patchValue({
      id: productAttrs.id,
      productId: productAttrs.productId,
      productAttributeId: productAttrs.productAttributeId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productAttrs = this.createFromForm();
    if (productAttrs.id !== undefined) {
      this.subscribeToSaveResponse(this.productAttrsService.update(productAttrs));
    } else {
      this.subscribeToSaveResponse(this.productAttrsService.create(productAttrs));
    }
  }

  private createFromForm(): IProductAttrs {
    return {
      ...new ProductAttrs(),
      id: this.editForm.get(['id'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      productAttributeId: this.editForm.get(['productAttributeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttrs>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
