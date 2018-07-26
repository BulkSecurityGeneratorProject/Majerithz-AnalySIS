import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetSubType } from 'app/shared/model/asset-sub-type.model';
import { AssetSubTypeService } from './asset-sub-type.service';

@Component({
    selector: 'jhi-asset-sub-type-delete-dialog',
    templateUrl: './asset-sub-type-delete-dialog.component.html'
})
export class AssetSubTypeDeleteDialogComponent {
    assetSubType: IAssetSubType;

    constructor(
        private assetSubTypeService: AssetSubTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assetSubTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'assetSubTypeListModification',
                content: 'Deleted an assetSubType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-asset-sub-type-delete-popup',
    template: ''
})
export class AssetSubTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assetSubType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AssetSubTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.assetSubType = assetSubType;
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
