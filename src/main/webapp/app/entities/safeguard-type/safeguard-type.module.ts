import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    SafeguardTypeComponent,
    SafeguardTypeDetailComponent,
    SafeguardTypeUpdateComponent,
    SafeguardTypeDeletePopupComponent,
    SafeguardTypeDeleteDialogComponent,
    safeguardTypeRoute,
    safeguardTypePopupRoute
} from './';

const ENTITY_STATES = [...safeguardTypeRoute, ...safeguardTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SafeguardTypeComponent,
        SafeguardTypeDetailComponent,
        SafeguardTypeUpdateComponent,
        SafeguardTypeDeleteDialogComponent,
        SafeguardTypeDeletePopupComponent
    ],
    entryComponents: [
        SafeguardTypeComponent,
        SafeguardTypeUpdateComponent,
        SafeguardTypeDeleteDialogComponent,
        SafeguardTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisSafeguardTypeModule {}
