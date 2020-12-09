import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttrsUpdateComponent } from 'app/entities/product-attrs/product-attrs-update.component';
import { ProductAttrsService } from 'app/entities/product-attrs/product-attrs.service';
import { ProductAttrs } from 'app/shared/model/product-attrs.model';

describe('Component Tests', () => {
  describe('ProductAttrs Management Update Component', () => {
    let comp: ProductAttrsUpdateComponent;
    let fixture: ComponentFixture<ProductAttrsUpdateComponent>;
    let service: ProductAttrsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttrsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductAttrsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttrsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttrsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductAttrs(123);
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
        const entity = new ProductAttrs();
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
