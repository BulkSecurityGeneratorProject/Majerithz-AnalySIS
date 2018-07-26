import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThreatType } from 'app/shared/model/threat-type.model';
import { ThreatTypeService } from './threat-type.service';

@Component({
    selector: 'jhi-threat-type-delete-dialog',
    templateUrl: './threat-type-delete-dialog.component.html'
})
export class ThreatTypeDeleteDialogComponent {
    threatType: IThreatType;

    constructor(private threatTypeService: ThreatTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.threatTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'threatTypeListModification',
                content: 'Deleted an threatType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-threat-type-delete-popup',
    template: ''
})
export class ThreatTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ threatType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ThreatTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.threatType = threatType;
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
