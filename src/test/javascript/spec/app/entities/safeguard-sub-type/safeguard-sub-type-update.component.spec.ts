/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardSubTypeUpdateComponent } from 'app/entities/safeguard-sub-type/safeguard-sub-type-update.component';
import { SafeguardSubTypeService } from 'app/entities/safeguard-sub-type/safeguard-sub-type.service';
import { SafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

describe('Component Tests', () => {
    describe('SafeguardSubType Management Update Component', () => {
        let comp: SafeguardSubTypeUpdateComponent;
        let fixture: ComponentFixture<SafeguardSubTypeUpdateComponent>;
        let service: SafeguardSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardSubTypeUpdateComponent]
            })
                .overrideTemplate(SafeguardSubTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardSubTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardSubTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SafeguardSubType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardSubType = entity;
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
                    const entity = new SafeguardSubType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardSubType = entity;
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
