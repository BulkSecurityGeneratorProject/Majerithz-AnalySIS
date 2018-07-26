import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDependence } from 'app/shared/model/dependence.model';

@Component({
    selector: 'jhi-dependence-detail',
    templateUrl: './dependence-detail.component.html'
})
export class DependenceDetailComponent implements OnInit {
    dependence: IDependence;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ dependence }) => {
            this.dependence = dependence;
        });
    }

    previousState() {
        window.history.back();
    }
}
