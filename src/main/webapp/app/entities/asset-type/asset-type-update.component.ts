import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAssetType } from 'app/shared/model/asset-type.model';
import { AssetTypeService } from './asset-type.service';

@Component({
    selector: 'jhi-asset-type-update',
    templateUrl: './asset-type-update.component.html'
})
export class AssetTypeUpdateComponent implements OnInit {
    private _assetType: IAssetType;
    isSaving: boolean;

    constructor(private assetTypeService: AssetTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ assetType }) => {
            this.assetType = assetType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.assetType.id !== undefined) {
            this.subscribeToSaveResponse(this.assetTypeService.update(this.assetType));
        } else {
            this.subscribeToSaveResponse(this.assetTypeService.create(this.assetType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAssetType>>) {
        result.subscribe((res: HttpResponse<IAssetType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get assetType() {
        return this._assetType;
    }

    set assetType(assetType: IAssetType) {
        this._assetType = assetType;
    }
}
