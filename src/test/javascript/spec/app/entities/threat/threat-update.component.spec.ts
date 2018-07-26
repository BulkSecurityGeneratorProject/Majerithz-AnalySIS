/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatUpdateComponent } from 'app/entities/threat/threat-update.component';
import { ThreatService } from 'app/entities/threat/threat.service';
import { Threat } from 'app/shared/model/threat.model';

describe('Component Tests', () => {
    describe('Threat Management Update Component', () => {
        let comp: ThreatUpdateComponent;
        let fixture: ComponentFixture<ThreatUpdateComponent>;
        let service: ThreatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatUpdateComponent]
            })
                .overrideTemplate(ThreatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ThreatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Threat(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threat = entity;
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
                    const entity = new Threat();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.threat = entity;
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
