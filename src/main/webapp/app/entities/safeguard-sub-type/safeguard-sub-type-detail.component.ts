import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

@Component({
    selector: 'jhi-safeguard-sub-type-detail',
    templateUrl: './safeguard-sub-type-detail.component.html'
})
export class SafeguardSubTypeDetailComponent implements OnInit {
    safeguardSubType: ISafeguardSubType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardSubType }) => {
            this.safeguardSubType = safeguardSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
