/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardTypeDeleteDialogComponent } from 'app/entities/safeguard-type/safeguard-type-delete-dialog.component';
import { SafeguardTypeService } from 'app/entities/safeguard-type/safeguard-type.service';

describe('Component Tests', () => {
    describe('SafeguardType Management Delete Component', () => {
        let comp: SafeguardTypeDeleteDialogComponent;
        let fixture: ComponentFixture<SafeguardTypeDeleteDialogComponent>;
        let service: SafeguardTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardTypeDeleteDialogComponent]
            })
                .overrideTemplate(SafeguardTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardTypeService);
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
