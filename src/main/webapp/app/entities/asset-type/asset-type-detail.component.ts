import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAssetType } from 'app/shared/model/asset-type.model';

@Component({
    selector: 'jhi-asset-type-detail',
    templateUrl: './asset-type-detail.component.html'
})
export class AssetTypeDetailComponent implements OnInit {
    assetType: IAssetType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ assetType }) => {
            this.assetType = assetType;
        });
    }

    previousState() {
        window.history.back();
    }
}
