/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ThreatSubTypeDeleteDialogComponent } from 'app/entities/threat-sub-type/threat-sub-type-delete-dialog.component';
import { ThreatSubTypeService } from 'app/entities/threat-sub-type/threat-sub-type.service';

describe('Component Tests', () => {
    describe('ThreatSubType Management Delete Component', () => {
        let comp: ThreatSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<ThreatSubTypeDeleteDialogComponent>;
        let service: ThreatSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ThreatSubTypeDeleteDialogComponent]
            })
                .overrideTemplate(ThreatSubTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ThreatSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ThreatSubTypeService);
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
