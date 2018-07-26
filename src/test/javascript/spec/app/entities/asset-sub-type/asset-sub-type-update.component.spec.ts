/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetSubTypeUpdateComponent } from 'app/entities/asset-sub-type/asset-sub-type-update.component';
import { AssetSubTypeService } from 'app/entities/asset-sub-type/asset-sub-type.service';
import { AssetSubType } from 'app/shared/model/asset-sub-type.model';

describe('Component Tests', () => {
    describe('AssetSubType Management Update Component', () => {
        let comp: AssetSubTypeUpdateComponent;
        let fixture: ComponentFixture<AssetSubTypeUpdateComponent>;
        let service: AssetSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetSubTypeUpdateComponent]
            })
                .overrideTemplate(AssetSubTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssetSubTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetSubTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AssetSubType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assetSubType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AssetSubType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assetSubType = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
