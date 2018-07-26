/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { DependenceUpdateComponent } from 'app/entities/dependence/dependence-update.component';
import { DependenceService } from 'app/entities/dependence/dependence.service';
import { Dependence } from 'app/shared/model/dependence.model';

describe('Component Tests', () => {
    describe('Dependence Management Update Component', () => {
        let comp: DependenceUpdateComponent;
        let fixture: ComponentFixture<DependenceUpdateComponent>;
        let service: DependenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [DependenceUpdateComponent]
            })
                .overrideTemplate(DependenceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DependenceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependenceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Dependence(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dependence = entity;
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
                    const entity = new Dependence();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dependence = entity;
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
