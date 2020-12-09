import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ShipmentTypeUpdateComponent } from 'app/entities/shipment-type/shipment-type-update.component';
import { ShipmentTypeService } from 'app/entities/shipment-type/shipment-type.service';
import { ShipmentType } from 'app/shared/model/shipment-type.model';

describe('Component Tests', () => {
  describe('ShipmentType Management Update Component', () => {
    let comp: ShipmentTypeUpdateComponent;
    let fixture: ComponentFixture<ShipmentTypeUpdateComponent>;
    let service: ShipmentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ShipmentTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ShipmentTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShipmentTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShipmentTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShipmentType(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ShipmentType();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
