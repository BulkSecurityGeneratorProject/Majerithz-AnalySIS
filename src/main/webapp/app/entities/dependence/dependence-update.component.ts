import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDependence } from 'app/shared/model/dependence.model';
import { DependenceService } from './dependence.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';

@Component({
    selector: 'jhi-dependence-update',
    templateUrl: './dependence-update.component.html'
})
export class DependenceUpdateComponent implements OnInit {
    private _dependence: IDependence;
    isSaving: boolean;

    assets: IAsset[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private dependenceService: DependenceService,
        private assetService: AssetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ dependence }) => {
            this.dependence = dependence;
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
        if (this.dependence.id !== undefined) {
            this.subscribeToSaveResponse(this.dependenceService.update(this.dependence));
        } else {
            this.subscribeToSaveResponse(this.dependenceService.create(this.dependence));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDependence>>) {
        result.subscribe((res: HttpResponse<IDependence>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get dependence() {
        return this._dependence;
    }

    set dependence(dependence: IDependence) {
        this._dependence = dependence;
    }
}
