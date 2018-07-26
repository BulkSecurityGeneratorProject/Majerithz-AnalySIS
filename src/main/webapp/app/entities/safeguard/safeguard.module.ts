import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    SafeguardComponent,
    SafeguardDetailComponent,
    SafeguardUpdateComponent,
    SafeguardDeletePopupComponent,
    SafeguardDeleteDialogComponent,
    safeguardRoute,
    safeguardPopupRoute
} from './';

const ENTITY_STATES = [...safeguardRoute, ...safeguardPopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SafeguardComponent,
        SafeguardDetailComponent,
        SafeguardUpdateComponent,
        SafeguardDeleteDialogComponent,
        SafeguardDeletePopupComponent
    ],
    entryComponents: [SafeguardComponent, SafeguardUpdateComponent, SafeguardDeleteDialogComponent, SafeguardDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisSafeguardModule {}
