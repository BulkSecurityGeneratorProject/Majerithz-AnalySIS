import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';
import { SafeguardSubTypeService } from './safeguard-sub-type.service';
import { ISafeguardType } from 'app/shared/model/safeguard-type.model';
import { SafeguardTypeService } from 'app/entities/safeguard-type';

@Component({
    selector: 'jhi-safeguard-sub-type-update',
    templateUrl: './safeguard-sub-type-update.component.html'
})
export class SafeguardSubTypeUpdateComponent implements OnInit {
    private _safeguardSubType: ISafeguardSubType;
    isSaving: boolean;

    safeguardtypes: ISafeguardType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private safeguardSubTypeService: SafeguardSubTypeService,
        private safeguardTypeService: SafeguardTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ safeguardSubType }) => {
            this.safeguardSubType = safeguardSubType;
        });
        this.safeguardTypeService.query().subscribe(
            (res: HttpResponse<ISafeguardType[]>) => {
                this.safeguardtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.safeguardSubType.id !== undefined) {
            this.subscribeToSaveResponse(this.safeguardSubTypeService.update(this.safeguardSubType));
        } else {
            this.subscribeToSaveResponse(this.safeguardSubTypeService.create(this.safeguardSubType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISafeguardSubType>>) {
        result.subscribe((res: HttpResponse<ISafeguardSubType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSafeguardTypeById(index: number, item: ISafeguardType) {
        return item.id;
    }
    get safeguardSubType() {
        return this._safeguardSubType;
    }

    set safeguardSubType(safeguardSubType: ISafeguardSubType) {
        this._safeguardSubType = safeguardSubType;
    }
}
