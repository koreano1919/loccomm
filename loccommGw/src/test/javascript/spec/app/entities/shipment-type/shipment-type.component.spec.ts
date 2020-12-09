import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LoccommGwTestModule } from '../../../test.module';
import { ShipmentTypeComponent } from 'app/entities/shipment-type/shipment-type.component';
import { ShipmentTypeService } from 'app/entities/shipment-type/shipment-type.service';
import { ShipmentType } from 'app/shared/model/shipment-type.model';

describe('Component Tests', () => {
  describe('ShipmentType Management Component', () => {
    let comp: ShipmentTypeComponent;
    let fixture: ComponentFixture<ShipmentTypeComponent>;
    let service: ShipmentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LoccommGwTestModule],
        declarations: [ShipmentTypeComponent],
      })
        .overrideTemplate(ShipmentTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ShipmentTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ShipmentTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ShipmentType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.shipmentTypes && comp.shipmentTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
