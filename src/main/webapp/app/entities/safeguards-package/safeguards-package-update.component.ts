import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';
import { SafeguardsPackageService } from './safeguards-package.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { ISafeguard } from 'app/shared/model/safeguard.model';
import { SafeguardService } from 'app/entities/safeguard';

@Component({
    selector: 'jhi-safeguards-package-update',
    templateUrl: './safeguards-package-update.component.html'
})
export class SafeguardsPackageUpdateComponent implements OnInit {
    private _safeguardsPackage: ISafeguardsPackage;
    isSaving: boolean;

    assets: IAsset[];

    safeguards: ISafeguard[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private safeguardsPackageService: SafeguardsPackageService,
        private assetService: AssetService,
        private safeguardService: SafeguardService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ safeguardsPackage }) => {
            this.safeguardsPackage = safeguardsPackage;
        });
        this.assetService.query().subscribe(
            (res: HttpResponse<IAsset[]>) => {
                this.assets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.safeguardService.query().subscribe(
            (res: HttpResponse<ISafeguard[]>) => {
                this.safeguards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.safeguardsPackage.id !== undefined) {
            this.subscribeToSaveResponse(this.safeguardsPackageService.update(this.safeguardsPackage));
        } else {
            this.subscribeToSaveResponse(this.safeguardsPackageService.create(this.safeguardsPackage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISafeguardsPackage>>) {
        result.subscribe((res: HttpResponse<ISafeguardsPackage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAssetById(index: number, item: IAsset) {
        return item.id;
    }

    trackSafeguardById(index: number, item: ISafeguard) {
        return item.id;
    }
    get safeguardsPackage() {
        return this._safeguardsPackage;
    }

    set safeguardsPackage(safeguardsPackage: ISafeguardsPackage) {
        this._safeguardsPackage = safeguardsPackage;
    }
}
