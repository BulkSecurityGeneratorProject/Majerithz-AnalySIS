import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AssetSubType } from 'app/shared/model/asset-sub-type.model';
import { AssetSubTypeService } from './asset-sub-type.service';
import { AssetSubTypeComponent } from './asset-sub-type.component';
import { AssetSubTypeDetailComponent } from './asset-sub-type-detail.component';
import { AssetSubTypeUpdateComponent } from './asset-sub-type-update.component';
import { AssetSubTypeDeletePopupComponent } from './asset-sub-type-delete-dialog.component';
import { IAssetSubType } from 'app/shared/model/asset-sub-type.model';

@Injectable({ providedIn: 'root' })
export class AssetSubTypeResolve implements Resolve<IAssetSubType> {
    constructor(private service: AssetSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((assetSubType: HttpResponse<AssetSubType>) => assetSubType.body));
        }
        return of(new AssetSubType());
    }
}

export const assetSubTypeRoute: Routes = [
    {
        path: 'asset-sub-type',
        component: AssetSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-sub-type/:id/view',
        component: AssetSubTypeDetailComponent,
        resolve: {
            assetSubType: AssetSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-sub-type/new',
        component: AssetSubTypeUpdateComponent,
        resolve: {
            assetSubType: AssetSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-sub-type/:id/edit',
        component: AssetSubTypeUpdateComponent,
        resolve: {
            assetSubType: AssetSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assetSubTypePopupRoute: Routes = [
    {
        path: 'asset-sub-type/:id/delete',
        component: AssetSubTypeDeletePopupComponent,
        resolve: {
            assetSubType: AssetSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
