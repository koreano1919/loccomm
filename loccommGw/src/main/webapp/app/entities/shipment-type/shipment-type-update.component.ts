import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IShipmentType, ShipmentType } from 'app/shared/model/shipment-type.model';
import { ShipmentTypeService } from './shipment-type.service';

@Component({
  selector: 'jhi-shipment-type-update',
  templateUrl: './shipment-type-update.component.html',
})
export class ShipmentTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    value: [],
    description: [],
  });

  constructor(protected shipmentTypeService: ShipmentTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shipmentType }) => {
      this.updateForm(shipmentType);
    });
  }

  updateForm(shipmentType: IShipmentType): void {
    this.editForm.patchValue({
      id: shipmentType.id,
      name: shipmentType.name,
      value: shipmentType.value,
      description: shipmentType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const shipmentType = this.createFromForm();
    if (shipmentType.id !== undefined) {
      this.subscribeToSaveResponse(this.shipmentTypeService.update(shipmentType));
    } else {
      this.subscribeToSaveResponse(this.shipmentTypeService.create(shipmentType));
    }
  }

  private createFromForm(): IShipmentType {
    return {
      ...new ShipmentType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      value: this.editForm.get(['value'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IShipmentType>>): void {
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
