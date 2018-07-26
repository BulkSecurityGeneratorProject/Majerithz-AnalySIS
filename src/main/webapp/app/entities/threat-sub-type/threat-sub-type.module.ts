import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    ThreatSubTypeComponent,
    ThreatSubTypeDetailComponent,
    ThreatSubTypeUpdateComponent,
    ThreatSubTypeDeletePopupComponent,
    ThreatSubTypeDeleteDialogComponent,
    threatSubTypeRoute,
    threatSubTypePopupRoute
} from './';

const ENTITY_STATES = [...threatSubTypeRoute, ...threatSubTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ThreatSubTypeComponent,
        ThreatSubTypeDetailComponent,
        ThreatSubTypeUpdateComponent,
        ThreatSubTypeDeleteDialogComponent,
        ThreatSubTypeDeletePopupComponent
    ],
    entryComponents: [
        ThreatSubTypeComponent,
        ThreatSubTypeUpdateComponent,
        ThreatSubTypeDeleteDialogComponent,
        ThreatSubTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisThreatSubTypeModule {}
