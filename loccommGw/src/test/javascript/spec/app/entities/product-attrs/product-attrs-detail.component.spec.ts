import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttrsDetailComponent } from 'app/entities/product-attrs/product-attrs-detail.component';
import { ProductAttrs } from 'app/shared/model/product-attrs.model';

describe('Component Tests', () => {
  describe('ProductAttrs Management Detail Component', () => {
    let comp: ProductAttrsDetailComponent;
    let fixture: ComponentFixture<ProductAttrsDetailComponent>;
    const route = ({ data: of({ productAttrs: new ProductAttrs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttrsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductAttrsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAttrsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productAttrs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productAttrs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
