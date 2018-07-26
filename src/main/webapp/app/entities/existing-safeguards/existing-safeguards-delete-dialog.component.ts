import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';
import { ExistingSafeguardsService } from './existing-safeguards.service';

@Component({
    selector: 'jhi-existing-safeguards-delete-dialog',
    templateUrl: './existing-safeguards-delete-dialog.component.html'
})
export class ExistingSafeguardsDeleteDialogComponent {
    existingSafeguards: IExistingSafeguards;

    constructor(
        private existingSafeguardsService: ExistingSafeguardsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.existingSafeguardsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'existingSafeguardsListModification',
                content: 'Deleted an existingSafeguards'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-existing-safeguards-delete-popup',
    template: ''
})
export class ExistingSafeguardsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ existingSafeguards }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ExistingSafeguardsDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.existingSafeguards = existingSafeguards;
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
