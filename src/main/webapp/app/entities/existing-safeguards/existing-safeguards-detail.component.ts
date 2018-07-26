import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

@Component({
    selector: 'jhi-existing-safeguards-detail',
    templateUrl: './existing-safeguards-detail.component.html'
})
export class ExistingSafeguardsDetailComponent implements OnInit {
    existingSafeguards: IExistingSafeguards;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ existingSafeguards }) => {
            this.existingSafeguards = existingSafeguards;
        });
    }

    previousState() {
        window.history.back();
    }
}
