import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ExistingSafeguards } from 'app/shared/model/existing-safeguards.model';
import { ExistingSafeguardsService } from './existing-safeguards.service';
import { ExistingSafeguardsComponent } from './existing-safeguards.component';
import { ExistingSafeguardsDetailComponent } from './existing-safeguards-detail.component';
import { ExistingSafeguardsUpdateComponent } from './existing-safeguards-update.component';
import { ExistingSafeguardsDeletePopupComponent } from './existing-safeguards-delete-dialog.component';
import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

@Injectable({ providedIn: 'root' })
export class ExistingSafeguardsResolve implements Resolve<IExistingSafeguards> {
    constructor(private service: ExistingSafeguardsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((existingSafeguards: HttpResponse<ExistingSafeguards>) => existingSafeguards.body));
        }
        return of(new ExistingSafeguards());
    }
}

export const existingSafeguardsRoute: Routes = [
    {
        path: 'existing-safeguards',
        component: ExistingSafeguardsComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.existingSafeguards.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'existing-safeguards/:id/view',
        component: ExistingSafeguardsDetailComponent,
        resolve: {
            existingSafeguards: ExistingSafeguardsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.existingSafeguards.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'existing-safeguards/new',
        component: ExistingSafeguardsUpdateComponent,
        resolve: {
            existingSafeguards: ExistingSafeguardsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.existingSafeguards.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'existing-safeguards/:id/edit',
        component: ExistingSafeguardsUpdateComponent,
        resolve: {
            existingSafeguards: ExistingSafeguardsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.existingSafeguards.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const existingSafeguardsPopupRoute: Routes = [
    {
        path: 'existing-safeguards/:id/delete',
        component: ExistingSafeguardsDeletePopupComponent,
        resolve: {
            existingSafeguards: ExistingSafeguardsResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.existingSafeguards.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
