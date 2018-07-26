/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ExistingSafeguardsUpdateComponent } from 'app/entities/existing-safeguards/existing-safeguards-update.component';
import { ExistingSafeguardsService } from 'app/entities/existing-safeguards/existing-safeguards.service';
import { ExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

describe('Component Tests', () => {
    describe('ExistingSafeguards Management Update Component', () => {
        let comp: ExistingSafeguardsUpdateComponent;
        let fixture: ComponentFixture<ExistingSafeguardsUpdateComponent>;
        let service: ExistingSafeguardsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ExistingSafeguardsUpdateComponent]
            })
                .overrideTemplate(ExistingSafeguardsUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExistingSafeguardsUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExistingSafeguardsService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExistingSafeguards(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.existingSafeguards = entity;
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
                    const entity = new ExistingSafeguards();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.existingSafeguards = entity;
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
