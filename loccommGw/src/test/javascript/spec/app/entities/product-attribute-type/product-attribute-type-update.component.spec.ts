import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttributeTypeUpdateComponent } from 'app/entities/product-attribute-type/product-attribute-type-update.component';
import { ProductAttributeTypeService } from 'app/entities/product-attribute-type/product-attribute-type.service';
import { ProductAttributeType } from 'app/shared/model/product-attribute-type.model';

describe('Component Tests', () => {
  describe('ProductAttributeType Management Update Component', () => {
    let comp: ProductAttributeTypeUpdateComponent;
    let fixture: ComponentFixture<ProductAttributeTypeUpdateComponent>;
    let service: ProductAttributeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttributeTypeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductAttributeTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttributeTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductAttributeType(123);
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
        const entity = new ProductAttributeType();
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
