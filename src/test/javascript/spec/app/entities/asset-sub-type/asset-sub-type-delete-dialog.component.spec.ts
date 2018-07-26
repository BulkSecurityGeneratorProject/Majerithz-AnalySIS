/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { AssetSubTypeDeleteDialogComponent } from 'app/entities/asset-sub-type/asset-sub-type-delete-dialog.component';
import { AssetSubTypeService } from 'app/entities/asset-sub-type/asset-sub-type.service';

describe('Component Tests', () => {
    describe('AssetSubType Management Delete Component', () => {
        let comp: AssetSubTypeDeleteDialogComponent;
        let fixture: ComponentFixture<AssetSubTypeDeleteDialogComponent>;
        let service: AssetSubTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [AssetSubTypeDeleteDialogComponent]
            })
                .overrideTemplate(AssetSubTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AssetSubTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetSubTypeService);
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
