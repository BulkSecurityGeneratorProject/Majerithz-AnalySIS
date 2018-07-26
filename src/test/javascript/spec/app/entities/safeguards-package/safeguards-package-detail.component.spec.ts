/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardsPackageDetailComponent } from 'app/entities/safeguards-package/safeguards-package-detail.component';
import { SafeguardsPackage } from 'app/shared/model/safeguards-package.model';

describe('Component Tests', () => {
    describe('SafeguardsPackage Management Detail Component', () => {
        let comp: SafeguardsPackageDetailComponent;
        let fixture: ComponentFixture<SafeguardsPackageDetailComponent>;
        const route = ({ data: of({ safeguardsPackage: new SafeguardsPackage(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardsPackageDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SafeguardsPackageDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardsPackageDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.safeguardsPackage).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
