/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatTypeDetailComponent } from 'app/entities/threat-type/threat-type-detail.component';
import { ThreatType } from 'app/shared/model/threat-type.model';

describe('Component Tests', () => {
    describe('ThreatType Management Detail Component', () => {
        let comp: ThreatTypeDetailComponent;
        let fixture: ComponentFixture<ThreatTypeDetailComponent>;
        const route = ({ data: of({ threatType: new ThreatType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ThreatTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.threatType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
