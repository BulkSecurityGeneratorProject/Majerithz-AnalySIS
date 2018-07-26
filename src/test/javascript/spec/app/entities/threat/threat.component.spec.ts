/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatComponent } from 'app/entities/threat/threat.component';
import { ThreatService } from 'app/entities/threat/threat.service';
import { Threat } from 'app/shared/model/threat.model';

describe('Component Tests', () => {
    describe('Threat Management Component', () => {
        let comp: ThreatComponent;
        let fixture: ComponentFixture<ThreatComponent>;
        let service: ThreatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatComponent],
                providers: []
            })
                .overrideTemplate(ThreatComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Threat(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.threats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
