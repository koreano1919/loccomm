import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProductAttribute, ProductAttribute } from 'app/shared/model/product-attribute.model';
import { ProductAttributeService } from './product-attribute.service';
import { IProductAttributeType } from 'app/shared/model/product-attribute-type.model';
import { ProductAttributeTypeService } from 'app/entities/product-attribute-type/product-attribute-type.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

type SelectableEntity = IProductAttributeType | IProduct;

@Component({
  selector: 'jhi-product-attribute-update',
  templateUrl: './product-attribute-update.component.html',
})
export class ProductAttributeUpdateComponent implements OnInit {
  isSaving = false;
  productattributetypes: IProductAttributeType[] = [];
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    productId: [],
    name: [],
    value: [],
    description: [],
    type: [],
    productAttributeType: [],
    product: [],
  });

  constructor(
    protected productAttributeService: ProductAttributeService,
    protected productAttributeTypeService: ProductAttributeTypeService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAttribute }) => {
      this.updateForm(productAttribute);

      this.productAttributeTypeService
        .query({ filter: 'productattribute-is-null' })
        .pipe(
          map((res: HttpResponse<IProductAttributeType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IProductAttributeType[]) => {
          if (!productAttribute.productAttributeType || !productAttribute.productAttributeType.id) {
            this.productattributetypes = resBody;
          } else {
            this.productAttributeTypeService
              .find(productAttribute.productAttributeType.id)
              .pipe(
                map((subRes: HttpResponse<IProductAttributeType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProductAttributeType[]) => (this.productattributetypes = concatRes));
          }
        });

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(productAttribute: IProductAttribute): void {
    this.editForm.patchValue({
      id: productAttribute.id,
      productId: productAttribute.productId,
      name: productAttribute.name,
      value: productAttribute.value,
      description: productAttribute.description,
      type: productAttribute.type,
      productAttributeType: productAttribute.productAttributeType,
      product: productAttribute.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productAttribute = this.createFromForm();
    if (productAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.productAttributeService.update(productAttribute));
    } else {
      this.subscribeToSaveResponse(this.productAttributeService.create(productAttribute));
    }
  }

  private createFromForm(): IProductAttribute {
    return {
      ...new ProductAttribute(),
      id: this.editForm.get(['id'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      description: this.editForm.get(['description'])!.value,
      type: this.editForm.get(['type'])!.value,
      productAttributeType: this.editForm.get(['productAttributeType'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAttribute>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
