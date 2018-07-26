import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';
import { Principal } from 'app/core';
import { SafeguardSubTypeService } from './safeguard-sub-type.service';

@Component({
    selector: 'jhi-safeguard-sub-type',
    templateUrl: './safeguard-sub-type.component.html'
})
export class SafeguardSubTypeComponent implements OnInit, OnDestroy {
    safeguardSubTypes: ISafeguardSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private safeguardSubTypeService: SafeguardSubTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.safeguardSubTypeService.query().subscribe(
            (res: HttpResponse<ISafeguardSubType[]>) => {
                this.safeguardSubTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSafeguardSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISafeguardSubType) {
        return item.id;
    }

    registerChangeInSafeguardSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('safeguardSubTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
