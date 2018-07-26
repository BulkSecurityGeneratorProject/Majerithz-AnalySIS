import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    SafeguardsPackageComponent,
    SafeguardsPackageDetailComponent,
    SafeguardsPackageUpdateComponent,
    SafeguardsPackageDeletePopupComponent,
    SafeguardsPackageDeleteDialogComponent,
    safeguardsPackageRoute,
    safeguardsPackagePopupRoute
} from './';

const ENTITY_STATES = [...safeguardsPackageRoute, ...safeguardsPackagePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SafeguardsPackageComponent,
        SafeguardsPackageDetailComponent,
        SafeguardsPackageUpdateComponent,
        SafeguardsPackageDeleteDialogComponent,
        SafeguardsPackageDeletePopupComponent
    ],
    entryComponents: [
        SafeguardsPackageComponent,
        SafeguardsPackageUpdateComponent,
        SafeguardsPackageDeleteDialogComponent,
        SafeguardsPackageDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisSafeguardsPackageModule {}
