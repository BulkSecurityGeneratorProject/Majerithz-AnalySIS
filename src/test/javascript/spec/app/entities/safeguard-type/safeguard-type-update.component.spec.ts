/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardTypeUpdateComponent } from 'app/entities/safeguard-type/safeguard-type-update.component';
import { SafeguardTypeService } from 'app/entities/safeguard-type/safeguard-type.service';
import { SafeguardType } from 'app/shared/model/safeguard-type.model';

describe('Component Tests', () => {
    describe('SafeguardType Management Update Component', () => {
        let comp: SafeguardTypeUpdateComponent;
        let fixture: ComponentFixture<SafeguardTypeUpdateComponent>;
        let service: SafeguardTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardTypeUpdateComponent]
            })
                .overrideTemplate(SafeguardTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SafeguardType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardType = entity;
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
                    const entity = new SafeguardType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardType = entity;
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
