import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IThreat } from 'app/shared/model/threat.model';
import { ThreatService } from './threat.service';
import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';
import { ThreatSubTypeService } from 'app/entities/threat-sub-type';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';

@Component({
    selector: 'jhi-threat-update',
    templateUrl: './threat-update.component.html'
})
export class ThreatUpdateComponent implements OnInit {
    private _threat: IThreat;
    isSaving: boolean;

    threatsubtypes: IThreatSubType[];

    assets: IAsset[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private threatService: ThreatService,
        private threatSubTypeService: ThreatSubTypeService,
        private assetService: AssetService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ threat }) => {
            this.threat = threat;
        });
        this.threatSubTypeService.query({ filter: 'threat-is-null' }).subscribe(
            (res: HttpResponse<IThreatSubType[]>) => {
                if (!this.threat.threatSubType || !this.threat.threatSubType.id) {
                    this.threatsubtypes = res.body;
                } else {
                    this.threatSubTypeService.find(this.threat.threatSubType.id).subscribe(
                        (subRes: HttpResponse<IThreatSubType>) => {
                            this.threatsubtypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        if (this.threat.id !== undefined) {
            this.subscribeToSaveResponse(this.threatService.update(this.threat));
        } else {
            this.subscribeToSaveResponse(this.threatService.create(this.threat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IThreat>>) {
        result.subscribe((res: HttpResponse<IThreat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackThreatSubTypeById(index: number, item: IThreatSubType) {
        return item.id;
    }

    trackAssetById(index: number, item: IAsset) {
        return item.id;
    }
    get threat() {
        return this._threat;
    }

    set threat(threat: IThreat) {
        this._threat = threat;
    }
}
