/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardsPackageUpdateComponent } from 'app/entities/safeguards-package/safeguards-package-update.component';
import { SafeguardsPackageService } from 'app/entities/safeguards-package/safeguards-package.service';
import { SafeguardsPackage } from 'app/shared/model/safeguards-package.model';

describe('Component Tests', () => {
    describe('SafeguardsPackage Management Update Component', () => {
        let comp: SafeguardsPackageUpdateComponent;
        let fixture: ComponentFixture<SafeguardsPackageUpdateComponent>;
        let service: SafeguardsPackageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardsPackageUpdateComponent]
            })
                .overrideTemplate(SafeguardsPackageUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardsPackageUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardsPackageService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SafeguardsPackage(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardsPackage = entity;
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
                    const entity = new SafeguardsPackage();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.safeguardsPackage = entity;
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
