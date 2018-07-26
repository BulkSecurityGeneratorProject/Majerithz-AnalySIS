import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';
import { Principal } from 'app/core';
import { ExistingSafeguardsService } from './existing-safeguards.service';

@Component({
    selector: 'jhi-existing-safeguards',
    templateUrl: './existing-safeguards.component.html'
})
export class ExistingSafeguardsComponent implements OnInit, OnDestroy {
    existingSafeguards: IExistingSafeguards[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private existingSafeguardsService: ExistingSafeguardsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.existingSafeguardsService.query().subscribe(
            (res: HttpResponse<IExistingSafeguards[]>) => {
                this.existingSafeguards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInExistingSafeguards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IExistingSafeguards) {
        return item.id;
    }

    registerChangeInExistingSafeguards() {
        this.eventSubscriber = this.eventManager.subscribe('existingSafeguardsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
