import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISafeguardType } from 'app/shared/model/safeguard-type.model';
import { SafeguardTypeService } from './safeguard-type.service';

@Component({
    selector: 'jhi-safeguard-type-delete-dialog',
    templateUrl: './safeguard-type-delete-dialog.component.html'
})
export class SafeguardTypeDeleteDialogComponent {
    safeguardType: ISafeguardType;

    constructor(
        private safeguardTypeService: SafeguardTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.safeguardTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'safeguardTypeListModification',
                content: 'Deleted an safeguardType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-safeguard-type-delete-popup',
    template: ''
})
export class SafeguardTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SafeguardTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.safeguardType = safeguardType;
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
