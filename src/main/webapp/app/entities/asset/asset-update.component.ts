import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from './asset.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';
import { IAnalysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from 'app/entities/analysis';

@Component({
    selector: 'jhi-asset-update',
    templateUrl: './asset-update.component.html'
})
export class AssetUpdateComponent implements OnInit {
    private _asset: IAsset;
    isSaving: boolean;

    companies: ICompany[];

    analyses: IAnalysis[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private assetService: AssetService,
        private companyService: CompanyService,
        private analysisService: AnalysisService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ asset }) => {
            this.asset = asset;
        });
        this.companyService.query().subscribe(
            (res: HttpResponse<ICompany[]>) => {
                this.companies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.analysisService.query().subscribe(
            (res: HttpResponse<IAnalysis[]>) => {
                this.analyses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.asset.id !== undefined) {
            this.subscribeToSaveResponse(this.assetService.update(this.asset));
        } else {
            this.subscribeToSaveResponse(this.assetService.create(this.asset));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAsset>>) {
        result.subscribe((res: HttpResponse<IAsset>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompanyById(index: number, item: ICompany) {
        return item.id;
    }

    trackAnalysisById(index: number, item: IAnalysis) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get asset() {
        return this._asset;
    }

    set asset(asset: IAsset) {
        this._asset = asset;
    }
}
