import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDimension } from 'app/shared/model/dimension.model';
import { DimensionService } from './dimension.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';

@Component({
    selector: 'jhi-dimension-update',
    templateUrl: './dimension-update.component.html'
})
export class DimensionUpdateComponent implements OnInit {
    private _dimension: IDimension;
    isSaving: boolean;

    assets: IAsset[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dimensionService: DimensionService,
        private assetService: AssetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dimension }) => {
            this.dimension = dimension;
        });
        this.assetService.query().subscribe(
            (res: HttpResponse<IAsset[]>) => {
                this.assets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.dimension.id !== undefined) {
            this.subscribeToSaveResponse(this.dimensionService.update(this.dimension));
        } else {
            this.subscribeToSaveResponse(this.dimensionService.create(this.dimension));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDimension>>) {
        result.subscribe((res: HttpResponse<IDimension>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get dimension() {
        return this._dimension;
    }

    set dimension(dimension: IDimension) {
        this._dimension = dimension;
    }
}
