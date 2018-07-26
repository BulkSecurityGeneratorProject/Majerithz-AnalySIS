/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardDetailComponent } from 'app/entities/safeguard/safeguard-detail.component';
import { Safeguard } from 'app/shared/model/safeguard.model';

describe('Component Tests', () => {
    describe('Safeguard Management Detail Component', () => {
        let comp: SafeguardDetailComponent;
        let fixture: ComponentFixture<SafeguardDetailComponent>;
        const route = ({ data: of({ safeguard: new Safeguard(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SafeguardDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.safeguard).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
