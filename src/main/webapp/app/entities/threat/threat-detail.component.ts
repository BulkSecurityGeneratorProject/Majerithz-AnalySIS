import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThreat } from 'app/shared/model/threat.model';

@Component({
    selector: 'jhi-threat-detail',
    templateUrl: './threat-detail.component.html'
})
export class ThreatDetailComponent implements OnInit {
    threat: IThreat;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ threat }) => {
            this.threat = threat;
        });
    }

    previousState() {
        window.history.back();
    }
}
