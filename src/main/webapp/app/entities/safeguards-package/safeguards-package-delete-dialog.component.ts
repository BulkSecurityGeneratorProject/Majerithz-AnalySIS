import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';
import { SafeguardsPackageService } from './safeguards-package.service';

@Component({
    selector: 'jhi-safeguards-package-delete-dialog',
    templateUrl: './safeguards-package-delete-dialog.component.html'
})
export class SafeguardsPackageDeleteDialogComponent {
    safeguardsPackage: ISafeguardsPackage;

    constructor(
        private safeguardsPackageService: SafeguardsPackageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.safeguardsPackageService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'safeguardsPackageListModification',
                content: 'Deleted an safeguardsPackage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-safeguards-package-delete-popup',
    template: ''
})
export class SafeguardsPackageDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardsPackage }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SafeguardsPackageDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.safeguardsPackage = safeguardsPackage;
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
