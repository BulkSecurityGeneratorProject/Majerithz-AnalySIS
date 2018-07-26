import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    ThreatTypeComponent,
    ThreatTypeDetailComponent,
    ThreatTypeUpdateComponent,
    ThreatTypeDeletePopupComponent,
    ThreatTypeDeleteDialogComponent,
    threatTypeRoute,
    threatTypePopupRoute
} from './';

const ENTITY_STATES = [...threatTypeRoute, ...threatTypePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ThreatTypeComponent,
        ThreatTypeDetailComponent,
        ThreatTypeUpdateComponent,
        ThreatTypeDeleteDialogComponent,
        ThreatTypeDeletePopupComponent
    ],
    entryComponents: [ThreatTypeComponent, ThreatTypeUpdateComponent, ThreatTypeDeleteDialogComponent, ThreatTypeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisThreatTypeModule {}
