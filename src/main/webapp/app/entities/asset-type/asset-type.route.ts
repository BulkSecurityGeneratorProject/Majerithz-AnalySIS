import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AssetType } from 'app/shared/model/asset-type.model';
import { AssetTypeService } from './asset-type.service';
import { AssetTypeComponent } from './asset-type.component';
import { AssetTypeDetailComponent } from './asset-type-detail.component';
import { AssetTypeUpdateComponent } from './asset-type-update.component';
import { AssetTypeDeletePopupComponent } from './asset-type-delete-dialog.component';
import { IAssetType } from 'app/shared/model/asset-type.model';

@Injectable({ providedIn: 'root' })
export class AssetTypeResolve implements Resolve<IAssetType> {
    constructor(private service: AssetTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((assetType: HttpResponse<AssetType>) => assetType.body));
        }
        return of(new AssetType());
    }
}

export const assetTypeRoute: Routes = [
    {
        path: 'asset-type',
        component: AssetTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-type/:id/view',
        component: AssetTypeDetailComponent,
        resolve: {
            assetType: AssetTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-type/new',
        component: AssetTypeUpdateComponent,
        resolve: {
            assetType: AssetTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'asset-type/:id/edit',
        component: AssetTypeUpdateComponent,
        resolve: {
            assetType: AssetTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assetTypePopupRoute: Routes = [
    {
        path: 'asset-type/:id/delete',
        component: AssetTypeDeletePopupComponent,
        resolve: {
            assetType: AssetTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.assetType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
