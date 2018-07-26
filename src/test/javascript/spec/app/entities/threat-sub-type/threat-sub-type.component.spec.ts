/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatSubTypeComponent } from 'app/entities/threat-sub-type/threat-sub-type.component';
import { ThreatSubTypeService } from 'app/entities/threat-sub-type/threat-sub-type.service';
import { ThreatSubType } from 'app/shared/model/threat-sub-type.model';

describe('Component Tests', () => {
    describe('ThreatSubType Management Component', () => {
        let comp: ThreatSubTypeComponent;
        let fixture: ComponentFixture<ThreatSubTypeComponent>;
        let service: ThreatSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatSubTypeComponent],
                providers: []
            })
                .overrideTemplate(ThreatSubTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatSubTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ThreatSubType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.threatSubTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
