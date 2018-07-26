import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';
import { ThreatSubTypeService } from './threat-sub-type.service';

@Component({
    selector: 'jhi-threat-sub-type-delete-dialog',
    templateUrl: './threat-sub-type-delete-dialog.component.html'
})
export class ThreatSubTypeDeleteDialogComponent {
    threatSubType: IThreatSubType;

    constructor(
        private threatSubTypeService: ThreatSubTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.threatSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'threatSubTypeListModification',
                content: 'Deleted an threatSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-threat-sub-type-delete-popup',
    template: ''
})
export class ThreatSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ threatSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ThreatSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.threatSubType = threatSubType;
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
