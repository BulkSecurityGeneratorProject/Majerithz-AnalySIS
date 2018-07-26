import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';
import { Principal } from 'app/core';
import { ThreatSubTypeService } from './threat-sub-type.service';

@Component({
    selector: 'jhi-threat-sub-type',
    templateUrl: './threat-sub-type.component.html'
})
export class ThreatSubTypeComponent implements OnInit, OnDestroy {
    threatSubTypes: IThreatSubType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private threatSubTypeService: ThreatSubTypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.threatSubTypeService.query().subscribe(
            (res: HttpResponse<IThreatSubType[]>) => {
                this.threatSubTypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInThreatSubTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IThreatSubType) {
        return item.id;
    }

    registerChangeInThreatSubTypes() {
        this.eventSubscriber = this.eventManager.subscribe('threatSubTypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
