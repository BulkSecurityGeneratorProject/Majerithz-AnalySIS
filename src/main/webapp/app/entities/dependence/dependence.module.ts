import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MajerithzAnalySisSharedModule } from 'app/shared';
import {
    DependenceComponent,
    DependenceDetailComponent,
    DependenceUpdateComponent,
    DependenceDeletePopupComponent,
    DependenceDeleteDialogComponent,
    dependenceRoute,
    dependencePopupRoute
} from './';

const ENTITY_STATES = [...dependenceRoute, ...dependencePopupRoute];

@NgModule({
    imports: [MajerithzAnalySisSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DependenceComponent,
        DependenceDetailComponent,
        DependenceUpdateComponent,
        DependenceDeleteDialogComponent,
        DependenceDeletePopupComponent
    ],
    entryComponents: [DependenceComponent, DependenceUpdateComponent, DependenceDeleteDialogComponent, DependenceDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisDependenceModule {}
