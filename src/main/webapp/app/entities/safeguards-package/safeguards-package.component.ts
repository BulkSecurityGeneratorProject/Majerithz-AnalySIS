import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';
import { Principal } from 'app/core';
import { SafeguardsPackageService } from './safeguards-package.service';

@Component({
    selector: 'jhi-safeguards-package',
    templateUrl: './safeguards-package.component.html'
})
export class SafeguardsPackageComponent implements OnInit, OnDestroy {
    safeguardsPackages: ISafeguardsPackage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private safeguardsPackageService: SafeguardsPackageService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.safeguardsPackageService.query().subscribe(
            (res: HttpResponse<ISafeguardsPackage[]>) => {
                this.safeguardsPackages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSafeguardsPackages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISafeguardsPackage) {
        return item.id;
    }

    registerChangeInSafeguardsPackages() {
        this.eventSubscriber = this.eventManager.subscribe('safeguardsPackageListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
