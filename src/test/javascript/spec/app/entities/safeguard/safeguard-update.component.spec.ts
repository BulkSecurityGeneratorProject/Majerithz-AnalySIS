/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardUpdateComponent } from 'app/entities/safeguard/safeguard-update.component';
import { SafeguardService } from 'app/entities/safeguard/safeguard.service';
import { Safeguard } from 'app/shared/model/safeguard.model';

describe('Component Tests', () => {
    describe('Safeguard Management Update Component', () => {
        let comp: SafeguardUpdateComponent;
        let fixture: ComponentFixture<SafeguardUpdateComponent>;
        let service: SafeguardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardUpdateComponent]
            })
                .overrideTemplate(SafeguardUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Safeguard(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguard = entity;
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
                    const entity = new Safeguard();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguard = entity;
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
