import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LoccommGwTestModule } from '../../../test.module';
import { ShipmentTypeDetailComponent } from 'app/entities/shipment-type/shipment-type-detail.component';
import { ShipmentType } from 'app/shared/model/shipment-type.model';

describe('Component Tests', () => {
  describe('ShipmentType Management Detail Component', () => {
    let comp: ShipmentTypeDetailComponent;
    let fixture: ComponentFixture<ShipmentTypeDetailComponent>;
    const route = ({ data: of({ shipmentType: new ShipmentType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ShipmentTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ShipmentTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ShipmentTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load shipmentType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.shipmentType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
