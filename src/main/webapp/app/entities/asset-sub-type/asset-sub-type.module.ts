import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    AssetSubTypeComponent,
    AssetSubTypeDetailComponent,
    AssetSubTypeUpdateComponent,
    AssetSubTypeDeletePopupComponent,
    AssetSubTypeDeleteDialogComponent,
    assetSubTypeRoute,
    assetSubTypePopupRoute
} from './';

const ENTITY_STATES = [...assetSubTypeRoute, ...assetSubTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AssetSubTypeComponent,
        AssetSubTypeDetailComponent,
        AssetSubTypeUpdateComponent,
        AssetSubTypeDeleteDialogComponent,
        AssetSubTypeDeletePopupComponent
    ],
    entryComponents: [
        AssetSubTypeComponent,
        AssetSubTypeUpdateComponent,
        AssetSubTypeDeleteDialogComponent,
        AssetSubTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisAssetSubTypeModule {}
