import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISafeguard } from 'app/shared/model/safeguard.model';

@Component({
    selector: 'jhi-safeguard-detail',
    templateUrl: './safeguard-detail.component.html'
})
export class SafeguardDetailComponent implements OnInit {
    safeguard: ISafeguard;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ safeguard }) => {
            this.safeguard = safeguard;
        });
    }

    previousState() {
        window.history.back();
    }
}
