import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    ExistingSafeguardsComponent,
    ExistingSafeguardsDetailComponent,
    ExistingSafeguardsUpdateComponent,
    ExistingSafeguardsDeletePopupComponent,
    ExistingSafeguardsDeleteDialogComponent,
    existingSafeguardsRoute,
    existingSafeguardsPopupRoute
} from './';

const ENTITY_STATES = [...existingSafeguardsRoute, ...existingSafeguardsPopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ExistingSafeguardsComponent,
        ExistingSafeguardsDetailComponent,
        ExistingSafeguardsUpdateComponent,
        ExistingSafeguardsDeleteDialogComponent,
        ExistingSafeguardsDeletePopupComponent
    ],
    entryComponents: [
        ExistingSafeguardsComponent,
        ExistingSafeguardsUpdateComponent,
        ExistingSafeguardsDeleteDialogComponent,
        ExistingSafeguardsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisExistingSafeguardsModule {}
