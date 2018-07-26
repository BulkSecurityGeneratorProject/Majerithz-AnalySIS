import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAssetType } from 'app/shared/model/asset-type.model';
import { AssetTypeService } from './asset-type.service';

@Component({
    selector: 'jhi-asset-type-delete-dialog',
    templateUrl: './asset-type-delete-dialog.component.html'
})
export class AssetTypeDeleteDialogComponent {
    assetType: IAssetType;

    constructor(private assetTypeService: AssetTypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assetTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'assetTypeListModification',
                content: 'Deleted an assetType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-asset-type-delete-popup',
    template: ''
})
export class AssetTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assetType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AssetTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.assetType = assetType;
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
