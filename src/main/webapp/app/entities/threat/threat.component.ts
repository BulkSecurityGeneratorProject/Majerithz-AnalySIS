import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IThreat } from 'app/shared/model/threat.model';
import { Principal } from 'app/core';
import { ThreatService } from './threat.service';

@Component({
    selector: 'jhi-threat',
    templateUrl: './threat.component.html'
})
export class ThreatComponent implements OnInit, OnDestroy {
    threats: IThreat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private threatService: ThreatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.threatService.query().subscribe(
            (res: HttpResponse<IThreat[]>) => {
                this.threats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInThreats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IThreat) {
        return item.id;
    }

    registerChangeInThreats() {
        this.eventSubscriber = this.eventManager.subscribe('threatListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
