import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';
import { SafeguardSubTypeService } from './safeguard-sub-type.service';

@Component({
    selector: 'jhi-safeguard-sub-type-delete-dialog',
    templateUrl: './safeguard-sub-type-delete-dialog.component.html'
})
export class SafeguardSubTypeDeleteDialogComponent {
    safeguardSubType: ISafeguardSubType;

    constructor(
        private safeguardSubTypeService: SafeguardSubTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.safeguardSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'safeguardSubTypeListModification',
                content: 'Deleted an safeguardSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-safeguard-sub-type-delete-popup',
    template: ''
})
export class SafeguardSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SafeguardSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.safeguardSubType = safeguardSubType;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
