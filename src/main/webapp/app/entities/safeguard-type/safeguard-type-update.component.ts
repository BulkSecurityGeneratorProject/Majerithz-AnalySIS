import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISafeguardType } from 'app/shared/model/safeguard-type.model';
import { SafeguardTypeService } from './safeguard-type.service';

@Component({
    selector: 'jhi-safeguard-type-update',
    templateUrl: './safeguard-type-update.component.html'
})
export class SafeguardTypeUpdateComponent implements OnInit {
    private _safeguardType: ISafeguardType;
    isSaving: boolean;

    constructor(private safeguardTypeService: SafeguardTypeService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ safeguardType }) => {
            this.safeguardType = safeguardType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.safeguardType.id !== undefined) {
            this.subscribeToSaveResponse(this.safeguardTypeService.update(this.safeguardType));
        } else {
            this.subscribeToSaveResponse(this.safeguardTypeService.create(this.safeguardType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISafeguardType>>) {
        result.subscribe((res: HttpResponse<ISafeguardType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get safeguardType() {
        return this._safeguardType;
    }

    set safeguardType(safeguardType: ISafeguardType) {
        this._safeguardType = safeguardType;
    }
}
