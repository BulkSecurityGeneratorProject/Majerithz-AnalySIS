import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IThreatType } from 'app/shared/model/threat-type.model';
import { Principal } from 'app/core';
import { ThreatTypeService } from './threat-type.service';

@Component({
    selector: 'jhi-threat-type',
    templateUrl: './threat-type.component.html'
})
export class ThreatTypeComponent implements OnInit, OnDestroy {
    threatTypes: IThreatType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private threatTypeService: ThreatTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.threatTypeService.query().subscribe(
            (res: HttpResponse<IThreatType[]>) => {
                this.threatTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInThreatTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IThreatType) {
        return item.id;
    }

    registerChangeInThreatTypes() {
        this.eventSubscriber = this.eventManager.subscribe('threatTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
