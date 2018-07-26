import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    AssetComponent,
    AssetDetailComponent,
    AssetUpdateComponent,
    AssetDeletePopupComponent,
    AssetDeleteDialogComponent,
    assetRoute,
    assetPopupRoute
} from './';

const ENTITY_STATES = [...assetRoute, ...assetPopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AssetComponent, AssetDetailComponent, AssetUpdateComponent, AssetDeleteDialogComponent, AssetDeletePopupComponent],
    entryComponents: [AssetComponent, AssetUpdateComponent, AssetDeleteDialogComponent, AssetDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisAssetModule {}
