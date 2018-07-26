import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';
import { ThreatSubTypeService } from './threat-sub-type.service';
import { IThreatType } from 'app/shared/model/threat-type.model';
import { ThreatTypeService } from 'app/entities/threat-type';

@Component({
    selector: 'jhi-threat-sub-type-update',
    templateUrl: './threat-sub-type-update.component.html'
})
export class ThreatSubTypeUpdateComponent implements OnInit {
    private _threatSubType: IThreatSubType;
    isSaving: boolean;

    threattypes: IThreatType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private threatSubTypeService: ThreatSubTypeService,
        private threatTypeService: ThreatTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ threatSubType }) => {
            this.threatSubType = threatSubType;
        });
        this.threatTypeService.query().subscribe(
            (res: HttpResponse<IThreatType[]>) => {
                this.threattypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.threatSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.threatSubTypeService.update(this.threatSubType));
        } else {
            this.subscribeToSaveResponse(this.threatSubTypeService.create(this.threatSubType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IThreatSubType>>) {
        result.subscribe((res: HttpResponse<IThreatSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackThreatTypeById(index: number, item: IThreatType) {
        return item.id;
    }
    get threatSubType() {
        return this._threatSubType;
    }

    set threatSubType(threatSubType: IThreatSubType) {
        this._threatSubType = threatSubType;
    }
}
