import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISafeguard } from 'app/shared/model/safeguard.model';
import { SafeguardService } from './safeguard.service';
import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';
import { SafeguardSubTypeService } from 'app/entities/safeguard-sub-type';

@Component({
    selector: 'jhi-safeguard-update',
    templateUrl: './safeguard-update.component.html'
})
export class SafeguardUpdateComponent implements OnInit {
    private _safeguard: ISafeguard;
    isSaving: boolean;

    safeguardsubtypes: ISafeguardSubType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private safeguardService: SafeguardService,
        private safeguardSubTypeService: SafeguardSubTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ safeguard }) => {
            this.safeguard = safeguard;
        });
        this.safeguardSubTypeService.query({ filter: 'safeguard-is-null' }).subscribe(
            (res: HttpResponse<ISafeguardSubType[]>) => {
                if (!this.safeguard.safeguardSubType || !this.safeguard.safeguardSubType.id) {
                    this.safeguardsubtypes = res.body;
                } else {
                    this.safeguardSubTypeService.find(this.safeguard.safeguardSubType.id).subscribe(
                        (subRes: HttpResponse<ISafeguardSubType>) => {
                            this.safeguardsubtypes = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.safeguard.id !== undefined) {
            this.subscribeToSaveResponse(this.safeguardService.update(this.safeguard));
        } else {
            this.subscribeToSaveResponse(this.safeguardService.create(this.safeguard));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISafeguard>>) {
        result.subscribe((res: HttpResponse<ISafeguard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSafeguardSubTypeById(index: number, item: ISafeguardSubType) {
        return item.id;
    }
    get safeguard() {
        return this._safeguard;
    }

    set safeguard(safeguard: ISafeguard) {
        this._safeguard = safeguard;
    }
}
