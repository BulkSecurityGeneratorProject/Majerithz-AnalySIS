/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatTypeUpdateComponent } from 'app/entities/threat-type/threat-type-update.component';
import { ThreatTypeService } from 'app/entities/threat-type/threat-type.service';
import { ThreatType } from 'app/shared/model/threat-type.model';

describe('Component Tests', () => {
    describe('ThreatType Management Update Component', () => {
        let comp: ThreatTypeUpdateComponent;
        let fixture: ComponentFixture<ThreatTypeUpdateComponent>;
        let service: ThreatTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatTypeUpdateComponent]
            })
                .overrideTemplate(ThreatTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ThreatType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threatType = entity;
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
                    const entity = new ThreatType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threatType = entity;
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
