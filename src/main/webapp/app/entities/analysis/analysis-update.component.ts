import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAnalysis } from 'app/shared/model/analysis.model';
import { AnalysisService } from './analysis.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company';

@Component({
    selector: 'jhi-analysis-update',
    templateUrl: './analysis-update.component.html'
})
export class AnalysisUpdateComponent implements OnInit {
    private _analysis: IAnalysis;
    isSaving: boolean;

    assets: IAsset[];

    companies: ICompany[];
    analysisCreationDate: string;
    analysisLastEdit: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private analysisService: AnalysisService,
        private assetService: AssetService,
        private companyService: CompanyService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ analysis }) => {
            this.analysis = analysis;
        });
        this.assetService.query().subscribe(
            (res: HttpResponse<IAsset[]>) => {
                this.assets = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.companyService.query().subscribe(
            (res: HttpResponse<ICompany[]>) => {
                this.companies = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.analysis.analysisCreationDate = moment(this.analysisCreationDate, DATE_TIME_FORMAT);
        this.analysis.analysisLastEdit = moment(this.analysisLastEdit, DATE_TIME_FORMAT);
        if (this.analysis.id !== undefined) {
            this.subscribeToSaveResponse(this.analysisService.update(this.analysis));
        } else {
            this.subscribeToSaveResponse(this.analysisService.create(this.analysis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAnalysis>>) {
        result.subscribe((res: HttpResponse<IAnalysis>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCompanyById(index: number, item: ICompany) {
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
    get analysis() {
        return this._analysis;
    }

    set analysis(analysis: IAnalysis) {
        this._analysis = analysis;
        this.analysisCreationDate = moment(analysis.analysisCreationDate).format(DATE_TIME_FORMAT);
        this.analysisLastEdit = moment(analysis.analysisLastEdit).format(DATE_TIME_FORMAT);
    }
}
