import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SafeguardType } from 'app/shared/model/safeguard-type.model';
import { SafeguardTypeService } from './safeguard-type.service';
import { SafeguardTypeComponent } from './safeguard-type.component';
import { SafeguardTypeDetailComponent } from './safeguard-type-detail.component';
import { SafeguardTypeUpdateComponent } from './safeguard-type-update.component';
import { SafeguardTypeDeletePopupComponent } from './safeguard-type-delete-dialog.component';
import { ISafeguardType } from 'app/shared/model/safeguard-type.model';

@Injectable({ providedIn: 'root' })
export class SafeguardTypeResolve implements Resolve<ISafeguardType> {
    constructor(private service: SafeguardTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((safeguardType: HttpResponse<SafeguardType>) => safeguardType.body));
        }
        return of(new SafeguardType());
    }
}

export const safeguardTypeRoute: Routes = [
    {
        path: 'safeguard-type',
        component: SafeguardTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-type/:id/view',
        component: SafeguardTypeDetailComponent,
        resolve: {
            safeguardType: SafeguardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-type/new',
        component: SafeguardTypeUpdateComponent,
        resolve: {
            safeguardType: SafeguardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard-type/:id/edit',
        component: SafeguardTypeUpdateComponent,
        resolve: {
            safeguardType: SafeguardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const safeguardTypePopupRoute: Routes = [
    {
        path: 'safeguard-type/:id/delete',
        component: SafeguardTypeDeletePopupComponent,
        resolve: {
            safeguardType: SafeguardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
