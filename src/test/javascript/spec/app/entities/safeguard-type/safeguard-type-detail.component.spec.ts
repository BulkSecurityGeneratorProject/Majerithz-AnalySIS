/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardTypeDetailComponent } from 'app/entities/safeguard-type/safeguard-type-detail.component';
import { SafeguardType } from 'app/shared/model/safeguard-type.model';

describe('Component Tests', () => {
    describe('SafeguardType Management Detail Component', () => {
        let comp: SafeguardTypeDetailComponent;
        let fixture: ComponentFixture<SafeguardTypeDetailComponent>;
        const route = ({ data: of({ safeguardType: new SafeguardType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SafeguardTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.safeguardType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
