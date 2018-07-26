import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISafeguard } from 'app/shared/model/safeguard.model';
import { SafeguardService } from './safeguard.service';

@Component({
    selector: 'jhi-safeguard-delete-dialog',
    templateUrl: './safeguard-delete-dialog.component.html'
})
export class SafeguardDeleteDialogComponent {
    safeguard: ISafeguard;

    constructor(private safeguardService: SafeguardService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.safeguardService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'safeguardListModification',
                content: 'Deleted an safeguard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-safeguard-delete-popup',
    template: ''
})
export class SafeguardDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguard }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SafeguardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.safeguard = safeguard;
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
