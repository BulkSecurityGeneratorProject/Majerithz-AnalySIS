/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardDeleteDialogComponent } from 'app/entities/safeguard/safeguard-delete-dialog.component';
import { SafeguardService } from 'app/entities/safeguard/safeguard.service';

describe('Component Tests', () => {
    describe('Safeguard Management Delete Component', () => {
        let comp: SafeguardDeleteDialogComponent;
        let fixture: ComponentFixture<SafeguardDeleteDialogComponent>;
        let service: SafeguardService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardDeleteDialogComponent]
            })
                .overrideTemplate(SafeguardDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SafeguardDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardService);
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
