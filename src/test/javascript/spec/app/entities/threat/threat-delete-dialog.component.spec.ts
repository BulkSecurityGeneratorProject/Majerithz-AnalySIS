/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatDeleteDialogComponent } from 'app/entities/threat/threat-delete-dialog.component';
import { ThreatService } from 'app/entities/threat/threat.service';

describe('Component Tests', () => {
    describe('Threat Management Delete Component', () => {
        let comp: ThreatDeleteDialogComponent;
        let fixture: ComponentFixture<ThreatDeleteDialogComponent>;
        let service: ThreatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatDeleteDialogComponent]
            })
                .overrideTemplate(ThreatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatService);
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
