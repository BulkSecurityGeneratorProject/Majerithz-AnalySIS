/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { DependenceDetailComponent } from 'app/entities/dependence/dependence-detail.component';
import { Dependence } from 'app/shared/model/dependence.model';

describe('Component Tests', () => {
    describe('Dependence Management Detail Component', () => {
        let comp: DependenceDetailComponent;
        let fixture: ComponentFixture<DependenceDetailComponent>;
        const route = ({ data: of({ dependence: new Dependence(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [DependenceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DependenceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DependenceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.dependence).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
