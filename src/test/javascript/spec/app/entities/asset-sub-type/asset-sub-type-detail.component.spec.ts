/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetSubTypeDetailComponent } from 'app/entities/asset-sub-type/asset-sub-type-detail.component';
import { AssetSubType } from 'app/shared/model/asset-sub-type.model';

describe('Component Tests', () => {
    describe('AssetSubType Management Detail Component', () => {
        let comp: AssetSubTypeDetailComponent;
        let fixture: ComponentFixture<AssetSubTypeDetailComponent>;
        const route = ({ data: of({ assetSubType: new AssetSubType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetSubTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AssetSubTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssetSubTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.assetSubType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
