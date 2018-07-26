/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatSubTypeUpdateComponent } from 'app/entities/threat-sub-type/threat-sub-type-update.component';
import { ThreatSubTypeService } from 'app/entities/threat-sub-type/threat-sub-type.service';
import { ThreatSubType } from 'app/shared/model/threat-sub-type.model';

describe('Component Tests', () => {
    describe('ThreatSubType Management Update Component', () => {
        let comp: ThreatSubTypeUpdateComponent;
        let fixture: ComponentFixture<ThreatSubTypeUpdateComponent>;
        let service: ThreatSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatSubTypeUpdateComponent]
            })
                .overrideTemplate(ThreatSubTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatSubTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatSubTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ThreatSubType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threatSubType = entity;
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
                    const entity = new ThreatSubType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threatSubType = entity;
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
