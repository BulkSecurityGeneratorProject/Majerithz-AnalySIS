import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISafeguardType } from 'app/shared/model/safeguard-type.model';
import { Principal } from 'app/core';
import { SafeguardTypeService } from './safeguard-type.service';

@Component({
    selector: 'jhi-safeguard-type',
    templateUrl: './safeguard-type.component.html'
})
export class SafeguardTypeComponent implements OnInit, OnDestroy {
    safeguardTypes: ISafeguardType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private safeguardTypeService: SafeguardTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.safeguardTypeService.query().subscribe(
            (res: HttpResponse<ISafeguardType[]>) => {
                this.safeguardTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSafeguardTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISafeguardType) {
        return item.id;
    }

    registerChangeInSafeguardTypes() {
        this.eventSubscriber = this.eventManager.subscribe('safeguardTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
