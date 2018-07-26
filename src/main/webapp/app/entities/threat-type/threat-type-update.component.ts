import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IThreatType } from 'app/shared/model/threat-type.model';
import { ThreatTypeService } from './threat-type.service';

@Component({
    selector: 'jhi-threat-type-update',
    templateUrl: './threat-type-update.component.html'
})
export class ThreatTypeUpdateComponent implements OnInit {
    private _threatType: IThreatType;
    isSaving: boolean;

    constructor(private threatTypeService: ThreatTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ threatType }) => {
            this.threatType = threatType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.threatType.id !== undefined) {
            this.subscribeToSaveResponse(this.threatTypeService.update(this.threatType));
        } else {
            this.subscribeToSaveResponse(this.threatTypeService.create(this.threatType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IThreatType>>) {
        result.subscribe((res: HttpResponse<IThreatType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get threatType() {
        return this._threatType;
    }

    set threatType(threatType: IThreatType) {
        this._threatType = threatType;
    }
}
