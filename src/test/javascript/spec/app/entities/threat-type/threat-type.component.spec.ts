/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatTypeComponent } from 'app/entities/threat-type/threat-type.component';
import { ThreatTypeService } from 'app/entities/threat-type/threat-type.service';
import { ThreatType } from 'app/shared/model/threat-type.model';

describe('Component Tests', () => {
    describe('ThreatType Management Component', () => {
        let comp: ThreatTypeComponent;
        let fixture: ComponentFixture<ThreatTypeComponent>;
        let service: ThreatTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatTypeComponent],
                providers: []
            })
                .overrideTemplate(ThreatTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ThreatType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.threatTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
