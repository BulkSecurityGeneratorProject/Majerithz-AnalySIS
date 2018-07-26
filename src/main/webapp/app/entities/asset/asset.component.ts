import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAsset } from 'app/shared/model/asset.model';
import { Principal } from 'app/core';
import { AssetService } from './asset.service';

@Component({
    selector: 'jhi-asset',
    templateUrl: './asset.component.html'
})
export class AssetComponent implements OnInit, OnDestroy {
    assets: IAsset[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private assetService: AssetService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.assetService.query().subscribe(
            (res: HttpResponse<IAsset[]>) => {
                this.assets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAssets();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAsset) {
        return item.id;
    }

    registerChangeInAssets() {
        this.eventSubscriber = this.eventManager.subscribe('assetListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
