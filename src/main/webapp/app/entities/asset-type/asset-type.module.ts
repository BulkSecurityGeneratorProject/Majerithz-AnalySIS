import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    AssetTypeComponent,
    AssetTypeDetailComponent,
    AssetTypeUpdateComponent,
    AssetTypeDeletePopupComponent,
    AssetTypeDeleteDialogComponent,
    assetTypeRoute,
    assetTypePopupRoute
} from './';

const ENTITY_STATES = [...assetTypeRoute, ...assetTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AssetTypeComponent,
        AssetTypeDetailComponent,
        AssetTypeUpdateComponent,
        AssetTypeDeleteDialogComponent,
        AssetTypeDeletePopupComponent
    ],
    entryComponents: [AssetTypeComponent, AssetTypeUpdateComponent, AssetTypeDeleteDialogComponent, AssetTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisAssetTypeModule {}
