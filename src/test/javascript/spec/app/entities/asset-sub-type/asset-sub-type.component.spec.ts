/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetSubTypeComponent } from 'app/entities/asset-sub-type/asset-sub-type.component';
import { AssetSubTypeService } from 'app/entities/asset-sub-type/asset-sub-type.service';
import { AssetSubType } from 'app/shared/model/asset-sub-type.model';

describe('Component Tests', () => {
    describe('AssetSubType Management Component', () => {
        let comp: AssetSubTypeComponent;
        let fixture: ComponentFixture<AssetSubTypeComponent>;
        let service: AssetSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetSubTypeComponent],
                providers: []
            })
                .overrideTemplate(AssetSubTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssetSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetSubTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new AssetSubType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.assetSubTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
