import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAssetSubType } from 'app/shared/model/asset-sub-type.model';
import { AssetSubTypeService } from './asset-sub-type.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { IAssetType } from 'app/shared/model/asset-type.model';
import { AssetTypeService } from 'app/entities/asset-type';

@Component({
    selector: 'jhi-asset-sub-type-update',
    templateUrl: './asset-sub-type-update.component.html'
})
export class AssetSubTypeUpdateComponent implements OnInit {
    private _assetSubType: IAssetSubType;
    isSaving: boolean;

    assets: IAsset[];

    assettypes: IAssetType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private assetSubTypeService: AssetSubTypeService,
        private assetService: AssetService,
        private assetTypeService: AssetTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ assetSubType }) => {
            this.assetSubType = assetSubType;
        });
        this.assetService.query().subscribe(
            (res: HttpResponse<IAsset[]>) => {
                this.assets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.assetTypeService.query().subscribe(
            (res: HttpResponse<IAssetType[]>) => {
                this.assettypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.assetSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.assetSubTypeService.update(this.assetSubType));
        } else {
            this.subscribeToSaveResponse(this.assetSubTypeService.create(this.assetSubType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAssetSubType>>) {
        result.subscribe((res: HttpResponse<IAssetSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAssetTypeById(index: number, item: IAssetType) {
        return item.id;
    }
    get assetSubType() {
        return this._assetSubType;
    }

    set assetSubType(assetSubType: IAssetSubType) {
        this._assetSubType = assetSubType;
    }
}
