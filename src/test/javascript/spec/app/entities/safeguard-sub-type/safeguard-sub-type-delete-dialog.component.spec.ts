/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardSubTypeDeleteDialogComponent } from 'app/entities/safeguard-sub-type/safeguard-sub-type-delete-dialog.component';
import { SafeguardSubTypeService } from 'app/entities/safeguard-sub-type/safeguard-sub-type.service';

describe('Component Tests', () => {
    describe('SafeguardSubType Management Delete Component', () => {
        let comp: SafeguardSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<SafeguardSubTypeDeleteDialogComponent>;
        let service: SafeguardSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardSubTypeDeleteDialogComponent]
            })
                .overrideTemplate(SafeguardSubTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardSubTypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
