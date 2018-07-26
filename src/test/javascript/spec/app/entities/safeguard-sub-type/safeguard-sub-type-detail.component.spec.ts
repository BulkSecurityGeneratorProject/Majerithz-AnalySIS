/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardSubTypeDetailComponent } from 'app/entities/safeguard-sub-type/safeguard-sub-type-detail.component';
import { SafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

describe('Component Tests', () => {
    describe('SafeguardSubType Management Detail Component', () => {
        let comp: SafeguardSubTypeDetailComponent;
        let fixture: ComponentFixture<SafeguardSubTypeDetailComponent>;
        const route = ({ data: of({ safeguardSubType: new SafeguardSubType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardSubTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SafeguardSubTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardSubTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.safeguardSubType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
