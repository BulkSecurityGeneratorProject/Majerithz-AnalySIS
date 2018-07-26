import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';

@Component({
    selector: 'jhi-safeguards-package-detail',
    templateUrl: './safeguards-package-detail.component.html'
})
export class SafeguardsPackageDetailComponent implements OnInit {
    safeguardsPackage: ISafeguardsPackage;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardsPackage }) => {
            this.safeguardsPackage = safeguardsPackage;
        });
    }

    previousState() {
        window.history.back();
    }
}
