import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';

@Component({
    selector: 'jhi-threat-sub-type-detail',
    templateUrl: './threat-sub-type-detail.component.html'
})
export class ThreatSubTypeDetailComponent implements OnInit {
    threatSubType: IThreatSubType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ threatSubType }) => {
            this.threatSubType = threatSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
