import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISafeguard } from 'app/shared/model/safeguard.model';
import { Principal } from 'app/core';
import { SafeguardService } from './safeguard.service';

@Component({
    selector: 'jhi-safeguard',
    templateUrl: './safeguard.component.html'
})
export class SafeguardComponent implements OnInit, OnDestroy {
    safeguards: ISafeguard[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private safeguardService: SafeguardService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.safeguardService.query().subscribe(
            (res: HttpResponse<ISafeguard[]>) => {
                this.safeguards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSafeguards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISafeguard) {
        return item.id;
    }

    registerChangeInSafeguards() {
        this.eventSubscriber = this.eventManager.subscribe('safeguardListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
