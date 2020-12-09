import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICartItem, CartItem } from 'app/shared/model/cart-item.model';
import { CartItemService } from './cart-item.service';
import { ICart } from 'app/shared/model/cart.model';
import { CartService } from 'app/entities/cart/cart.service';

@Component({
  selector: 'jhi-cart-item-update',
  templateUrl: './cart-item-update.component.html',
})
export class CartItemUpdateComponent implements OnInit {
  isSaving = false;
  carts: ICart[] = [];

  editForm = this.fb.group({
    id: [],
    productId: [],
    price: [],
    cart: [],
  });

  constructor(
    protected cartItemService: CartItemService,
    protected cartService: CartService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cartItem }) => {
      this.updateForm(cartItem);

      this.cartService.query().subscribe((res: HttpResponse<ICart[]>) => (this.carts = res.body || []));
    });
  }

  updateForm(cartItem: ICartItem): void {
    this.editForm.patchValue({
      id: cartItem.id,
      productId: cartItem.productId,
      price: cartItem.price,
      cart: cartItem.cart,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cartItem = this.createFromForm();
    if (cartItem.id !== undefined) {
      this.subscribeToSaveResponse(this.cartItemService.update(cartItem));
    } else {
      this.subscribeToSaveResponse(this.cartItemService.create(cartItem));
    }
  }

  private createFromForm(): ICartItem {
    return {
      ...new CartItem(),
      id: this.editForm.get(['id'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      price: this.editForm.get(['price'])!.value,
      cart: this.editForm.get(['cart'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICartItem>>): void {
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

  trackById(index: number, item: ICart): any {
    return item.id;
  }
}
