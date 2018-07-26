/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ExistingSafeguardsDeleteDialogComponent } from 'app/entities/existing-safeguards/existing-safeguards-delete-dialog.component';
import { ExistingSafeguardsService } from 'app/entities/existing-safeguards/existing-safeguards.service';

describe('Component Tests', () => {
    describe('ExistingSafeguards Management Delete Component', () => {
        let comp: ExistingSafeguardsDeleteDialogComponent;
        let fixture: ComponentFixture<ExistingSafeguardsDeleteDialogComponent>;
        let service: ExistingSafeguardsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ExistingSafeguardsDeleteDialogComponent]
            })
                .overrideTemplate(ExistingSafeguardsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExistingSafeguardsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExistingSafeguardsService);
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
