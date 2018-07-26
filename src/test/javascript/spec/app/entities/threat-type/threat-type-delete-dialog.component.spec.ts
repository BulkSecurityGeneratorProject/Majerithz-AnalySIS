/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatTypeDeleteDialogComponent } from 'app/entities/threat-type/threat-type-delete-dialog.component';
import { ThreatTypeService } from 'app/entities/threat-type/threat-type.service';

describe('Component Tests', () => {
    describe('ThreatType Management Delete Component', () => {
        let comp: ThreatTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ThreatTypeDeleteDialogComponent>;
        let service: ThreatTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatTypeDeleteDialogComponent]
            })
                .overrideTemplate(ThreatTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatTypeService);
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
