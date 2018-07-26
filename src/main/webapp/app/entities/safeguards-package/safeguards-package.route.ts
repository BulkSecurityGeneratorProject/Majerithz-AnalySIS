import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SafeguardsPackage } from 'app/shared/model/safeguards-package.model';
import { SafeguardsPackageService } from './safeguards-package.service';
import { SafeguardsPackageComponent } from './safeguards-package.component';
import { SafeguardsPackageDetailComponent } from './safeguards-package-detail.component';
import { SafeguardsPackageUpdateComponent } from './safeguards-package-update.component';
import { SafeguardsPackageDeletePopupComponent } from './safeguards-package-delete-dialog.component';
import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';

@Injectable({ providedIn: 'root' })
export class SafeguardsPackageResolve implements Resolve<ISafeguardsPackage> {
    constructor(private service: SafeguardsPackageService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((safeguardsPackage: HttpResponse<SafeguardsPackage>) => safeguardsPackage.body));
        }
        return of(new SafeguardsPackage());
    }
}

export const safeguardsPackageRoute: Routes = [
    {
        path: 'safeguards-package',
        component: SafeguardsPackageComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardsPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguards-package/:id/view',
        component: SafeguardsPackageDetailComponent,
        resolve: {
            safeguardsPackage: SafeguardsPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardsPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguards-package/new',
        component: SafeguardsPackageUpdateComponent,
        resolve: {
            safeguardsPackage: SafeguardsPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardsPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguards-package/:id/edit',
        component: SafeguardsPackageUpdateComponent,
        resolve: {
            safeguardsPackage: SafeguardsPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardsPackage.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const safeguardsPackagePopupRoute: Routes = [
    {
        path: 'safeguards-package/:id/delete',
        component: SafeguardsPackageDeletePopupComponent,
        resolve: {
            safeguardsPackage: SafeguardsPackageResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguardsPackage.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
