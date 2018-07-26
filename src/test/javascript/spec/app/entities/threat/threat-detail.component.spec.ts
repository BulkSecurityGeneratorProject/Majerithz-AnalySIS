/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatDetailComponent } from 'app/entities/threat/threat-detail.component';
import { Threat } from 'app/shared/model/threat.model';

describe('Component Tests', () => {
    describe('Threat Management Detail Component', () => {
        let comp: ThreatDetailComponent;
        let fixture: ComponentFixture<ThreatDetailComponent>;
        const route = ({ data: of({ threat: new Threat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ThreatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.threat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
