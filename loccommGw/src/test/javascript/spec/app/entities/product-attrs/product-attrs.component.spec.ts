import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttrsComponent } from 'app/entities/product-attrs/product-attrs.component';
import { ProductAttrsService } from 'app/entities/product-attrs/product-attrs.service';
import { ProductAttrs } from 'app/shared/model/product-attrs.model';

describe('Component Tests', () => {
  describe('ProductAttrs Management Component', () => {
    let comp: ProductAttrsComponent;
    let fixture: ComponentFixture<ProductAttrsComponent>;
    let service: ProductAttrsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttrsComponent],
      })
        .overrideTemplate(ProductAttrsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAttrsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAttrsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductAttrs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productAttrs && comp.productAttrs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
