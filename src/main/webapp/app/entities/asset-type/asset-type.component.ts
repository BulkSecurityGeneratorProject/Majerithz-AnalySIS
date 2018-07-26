import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAssetType } from 'app/shared/model/asset-type.model';
import { Principal } from 'app/core';
import { AssetTypeService } from './asset-type.service';

@Component({
    selector: 'jhi-asset-type',
    templateUrl: './asset-type.component.html'
})
export class AssetTypeComponent implements OnInit, OnDestroy {
    assetTypes: IAssetType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private assetTypeService: AssetTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.assetTypeService.query().subscribe(
            (res: HttpResponse<IAssetType[]>) => {
                this.assetTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAssetTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAssetType) {
        return item.id;
    }

    registerChangeInAssetTypes() {
        this.eventSubscriber = this.eventManager.subscribe('assetTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
