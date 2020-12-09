import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ProductAttributeTypeDetailComponent } from 'app/entities/product-attribute-type/product-attribute-type-detail.component';
import { ProductAttributeType } from 'app/shared/model/product-attribute-type.model';

describe('Component Tests', () => {
  describe('ProductAttributeType Management Detail Component', () => {
    let comp: ProductAttributeTypeDetailComponent;
    let fixture: ComponentFixture<ProductAttributeTypeDetailComponent>;
    const route = ({ data: of({ productAttributeType: new ProductAttributeType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ProductAttributeTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductAttributeTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAttributeTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productAttributeType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productAttributeType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
