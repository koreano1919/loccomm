import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttributeTypeComponent } from 'app/entities/product-attribute-type/product-attribute-type.component';
import { ProductAttributeTypeService } from 'app/entities/product-attribute-type/product-attribute-type.service';
import { ProductAttributeType } from 'app/shared/model/product-attribute-type.model';

describe('Component Tests', () => {
  describe('ProductAttributeType Management Component', () => {
    let comp: ProductAttributeTypeComponent;
    let fixture: ComponentFixture<ProductAttributeTypeComponent>;
    let service: ProductAttributeTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttributeTypeComponent],
      })
        .overrideTemplate(ProductAttributeTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttributeTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttributeTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductAttributeType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productAttributeTypes && comp.productAttributeTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
