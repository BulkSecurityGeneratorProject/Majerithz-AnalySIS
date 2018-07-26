import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';
import { ExistingSafeguardsService } from './existing-safeguards.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { ISafeguard } from 'app/shared/model/safeguard.model';
import { SafeguardService } from 'app/entities/safeguard';

@Component({
    selector: 'jhi-existing-safeguards-update',
    templateUrl: './existing-safeguards-update.component.html'
})
export class ExistingSafeguardsUpdateComponent implements OnInit {
    private _existingSafeguards: IExistingSafeguards;
    isSaving: boolean;

    assets: IAsset[];

    safeguards: ISafeguard[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private existingSafeguardsService: ExistingSafeguardsService,
        private assetService: AssetService,
        private safeguardService: SafeguardService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ existingSafeguards }) => {
            this.existingSafeguards = existingSafeguards;
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
        if (this.existingSafeguards.id !== undefined) {
            this.subscribeToSaveResponse(this.existingSafeguardsService.update(this.existingSafeguards));
        } else {
            this.subscribeToSaveResponse(this.existingSafeguardsService.create(this.existingSafeguards));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IExistingSafeguards>>) {
        result.subscribe((res: HttpResponse<IExistingSafeguards>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get existingSafeguards() {
        return this._existingSafeguards;
    }

    set existingSafeguards(existingSafeguards: IExistingSafeguards) {
        this._existingSafeguards = existingSafeguards;
    }
}
