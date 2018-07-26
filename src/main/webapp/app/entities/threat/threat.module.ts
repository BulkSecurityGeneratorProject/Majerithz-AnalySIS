import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    ThreatComponent,
    ThreatDetailComponent,
    ThreatUpdateComponent,
    ThreatDeletePopupComponent,
    ThreatDeleteDialogComponent,
    threatRoute,
    threatPopupRoute
} from './';

const ENTITY_STATES = [...threatRoute, ...threatPopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ThreatComponent, ThreatDetailComponent, ThreatUpdateComponent, ThreatDeleteDialogComponent, ThreatDeletePopupComponent],
    entryComponents: [ThreatComponent, ThreatUpdateComponent, ThreatDeleteDialogComponent, ThreatDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisThreatModule {}
