/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetTypeUpdateComponent } from 'app/entities/asset-type/asset-type-update.component';
import { AssetTypeService } from 'app/entities/asset-type/asset-type.service';
import { AssetType } from 'app/shared/model/asset-type.model';

describe('Component Tests', () => {
    describe('AssetType Management Update Component', () => {
        let comp: AssetTypeUpdateComponent;
        let fixture: ComponentFixture<AssetTypeUpdateComponent>;
        let service: AssetTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetTypeUpdateComponent]
            })
                .overrideTemplate(AssetTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AssetTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AssetType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assetType = entity;
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
                    const entity = new AssetType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.assetType = entity;
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
