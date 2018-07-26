import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDependence } from 'app/shared/model/dependence.model';
import { Principal } from 'app/core';
import { DependenceService } from './dependence.service';

@Component({
    selector: 'jhi-dependence',
    templateUrl: './dependence.component.html'
})
export class DependenceComponent implements OnInit, OnDestroy {
    dependences: IDependence[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dependenceService: DependenceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dependenceService.query().subscribe(
            (res: HttpResponse<IDependence[]>) => {
                this.dependences = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDependences();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDependence) {
        return item.id;
    }

    registerChangeInDependences() {
        this.eventSubscriber = this.eventManager.subscribe('dependenceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
