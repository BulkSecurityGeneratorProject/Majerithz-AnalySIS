/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ExistingSafeguardsDetailComponent } from 'app/entities/existing-safeguards/existing-safeguards-detail.component';
import { ExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

describe('Component Tests', () => {
    describe('ExistingSafeguards Management Detail Component', () => {
        let comp: ExistingSafeguardsDetailComponent;
        let fixture: ComponentFixture<ExistingSafeguardsDetailComponent>;
        const route = ({ data: of({ existingSafeguards: new ExistingSafeguards(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ExistingSafeguardsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExistingSafeguardsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExistingSafeguardsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.existingSafeguards).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
