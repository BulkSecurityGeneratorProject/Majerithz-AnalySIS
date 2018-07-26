import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssetSubType } from 'app/shared/model/asset-sub-type.model';

@Component({
    selector: 'jhi-asset-sub-type-detail',
    templateUrl: './asset-sub-type-detail.component.html'
})
export class AssetSubTypeDetailComponent implements OnInit {
    assetSubType: IAssetSubType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assetSubType }) => {
            this.assetSubType = assetSubType;
        });
    }

    previousState() {
        window.history.back();
    }
}
