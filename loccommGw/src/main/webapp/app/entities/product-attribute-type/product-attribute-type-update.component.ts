import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductAttributeType, ProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { ProductAttributeTypeService } from './product-attribute-type.service';

@Component({
  selector: 'jhi-product-attribute-type-update',
  templateUrl: './product-attribute-type-update.component.html',
})
export class ProductAttributeTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
    description: [],
  });

  constructor(
    protected productAttributeTypeService: ProductAttributeTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAttributeType }) => {
      this.updateForm(productAttributeType);
    });
  }

  updateForm(productAttributeType: IProductAttributeType): void {
    this.editForm.patchValue({
      id: productAttributeType.id,
      name: productAttributeType.name,
      value: productAttributeType.value,
      description: productAttributeType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productAttributeType = this.createFromForm();
    if (productAttributeType.id !== undefined) {
      this.subscribeToSaveResponse(this.productAttributeTypeService.update(productAttributeType));
    } else {
      this.subscribeToSaveResponse(this.productAttributeTypeService.create(productAttributeType));
    }
  }

  private createFromForm(): IProductAttributeType {
    return {
      ...new ProductAttributeType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttributeType>>): void {
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
