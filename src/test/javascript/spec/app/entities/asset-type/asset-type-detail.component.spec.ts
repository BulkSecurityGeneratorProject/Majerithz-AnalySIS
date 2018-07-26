/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetTypeDetailComponent } from 'app/entities/asset-type/asset-type-detail.component';
import { AssetType } from 'app/shared/model/asset-type.model';

describe('Component Tests', () => {
    describe('AssetType Management Detail Component', () => {
        let comp: AssetTypeDetailComponent;
        let fixture: ComponentFixture<AssetTypeDetailComponent>;
        const route = ({ data: of({ assetType: new AssetType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AssetTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssetTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.assetType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
