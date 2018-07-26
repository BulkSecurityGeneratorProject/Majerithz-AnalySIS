/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatSubTypeDetailComponent } from 'app/entities/threat-sub-type/threat-sub-type-detail.component';
import { ThreatSubType } from 'app/shared/model/threat-sub-type.model';

describe('Component Tests', () => {
    describe('ThreatSubType Management Detail Component', () => {
        let comp: ThreatSubTypeDetailComponent;
        let fixture: ComponentFixture<ThreatSubTypeDetailComponent>;
        const route = ({ data: of({ threatSubType: new ThreatSubType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatSubTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ThreatSubTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatSubTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.threatSubType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
