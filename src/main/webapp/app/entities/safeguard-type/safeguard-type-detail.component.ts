import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISafeguardType } from 'app/shared/model/safeguard-type.model';

@Component({
    selector: 'jhi-safeguard-type-detail',
    templateUrl: './safeguard-type-detail.component.html'
})
export class SafeguardTypeDetailComponent implements OnInit {
    safeguardType: ISafeguardType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguardType }) => {
            this.safeguardType = safeguardType;
        });
    }

    previousState() {
        window.history.back();
    }
}
