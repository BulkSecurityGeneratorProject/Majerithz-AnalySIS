import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAssetSubType } from 'app/shared/model/asset-sub-type.model';
import { Principal } from 'app/core';
import { AssetSubTypeService } from './asset-sub-type.service';

@Component({
    selector: 'jhi-asset-sub-type',
    templateUrl: './asset-sub-type.component.html'
})
export class AssetSubTypeComponent implements OnInit, OnDestroy {
    assetSubTypes: IAssetSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private assetSubTypeService: AssetSubTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.assetSubTypeService.query().subscribe(
            (res: HttpResponse<IAssetSubType[]>) => {
                this.assetSubTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAssetSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAssetSubType) {
        return item.id;
    }

    registerChangeInAssetSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('assetSubTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
