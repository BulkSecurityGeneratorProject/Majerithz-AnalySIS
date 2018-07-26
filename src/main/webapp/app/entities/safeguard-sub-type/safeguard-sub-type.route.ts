import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';
import { SafeguardSubTypeService } from './safeguard-sub-type.service';
import { SafeguardSubTypeComponent } from './safeguard-sub-type.component';
import { SafeguardSubTypeDetailComponent } from './safeguard-sub-type-detail.component';
import { SafeguardSubTypeUpdateComponent } from './safeguard-sub-type-update.component';
import { SafeguardSubTypeDeletePopupComponent } from './safeguard-sub-type-delete-dialog.component';
import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

@Injectable({ providedIn: 'root' })
export class SafeguardSubTypeResolve implements Resolve<ISafeguardSubType> {
    constructor(private service: SafeguardSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((safeguardSubType: HttpResponse<SafeguardSubType>) => safeguardSubType.body));
        }
        return of(new SafeguardSubType());
    }
}

export const safeguardSubTypeRoute: Routes = [
    {
        path: 'safeguard-sub-type',
        component: SafeguardSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-sub-type/:id/view',
        component: SafeguardSubTypeDetailComponent,
        resolve: {
            safeguardSubType: SafeguardSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-sub-type/new',
        component: SafeguardSubTypeUpdateComponent,
        resolve: {
            safeguardSubType: SafeguardSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-sub-type/:id/edit',
        component: SafeguardSubTypeUpdateComponent,
        resolve: {
            safeguardSubType: SafeguardSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const safeguardSubTypePopupRoute: Routes = [
    {
        path: 'safeguard-sub-type/:id/delete',
        component: SafeguardSubTypeDeletePopupComponent,
        resolve: {
            safeguardSubType: SafeguardSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
