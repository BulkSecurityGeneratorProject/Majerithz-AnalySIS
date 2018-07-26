import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThreatType } from 'app/shared/model/threat-type.model';

@Component({
    selector: 'jhi-threat-type-detail',
    templateUrl: './threat-type-detail.component.html'
})
export class ThreatTypeDetailComponent implements OnInit {
    threatType: IThreatType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ threatType }) => {
            this.threatType = threatType;
        });
    }

    previousState() {
        window.history.back();
    }
}
