/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetTypeComponent } from 'app/entities/asset-type/asset-type.component';
import { AssetTypeService } from 'app/entities/asset-type/asset-type.service';
import { AssetType } from 'app/shared/model/asset-type.model';

describe('Component Tests', () => {
    describe('AssetType Management Component', () => {
        let comp: AssetTypeComponent;
        let fixture: ComponentFixture<AssetTypeComponent>;
        let service: AssetTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetTypeComponent],
                providers: []
            })
                .overrideTemplate(AssetTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssetTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AssetType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.assetTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
