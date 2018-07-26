import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    SafeguardSubTypeComponent,
    SafeguardSubTypeDetailComponent,
    SafeguardSubTypeUpdateComponent,
    SafeguardSubTypeDeletePopupComponent,
    SafeguardSubTypeDeleteDialogComponent,
    safeguardSubTypeRoute,
    safeguardSubTypePopupRoute
} from './';

const ENTITY_STATES = [...safeguardSubTypeRoute, ...safeguardSubTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SafeguardSubTypeComponent,
        SafeguardSubTypeDetailComponent,
        SafeguardSubTypeUpdateComponent,
        SafeguardSubTypeDeleteDialogComponent,
        SafeguardSubTypeDeletePopupComponent
    ],
    entryComponents: [
        SafeguardSubTypeComponent,
        SafeguardSubTypeUpdateComponent,
        SafeguardSubTypeDeleteDialogComponent,
        SafeguardSubTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisSafeguardSubTypeModule {}
