import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Safeguard } from 'app/shared/model/safeguard.model';
import { SafeguardService } from './safeguard.service';
import { SafeguardComponent } from './safeguard.component';
import { SafeguardDetailComponent } from './safeguard-detail.component';
import { SafeguardUpdateComponent } from './safeguard-update.component';
import { SafeguardDeletePopupComponent } from './safeguard-delete-dialog.component';
import { ISafeguard } from 'app/shared/model/safeguard.model';

@Injectable({ providedIn: 'root' })
export class SafeguardResolve implements Resolve<ISafeguard> {
    constructor(private service: SafeguardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((safeguard: HttpResponse<Safeguard>) => safeguard.body));
        }
        return of(new Safeguard());
    }
}

export const safeguardRoute: Routes = [
    {
        path: 'safeguard',
        component: SafeguardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard/:id/view',
        component: SafeguardDetailComponent,
        resolve: {
            safeguard: SafeguardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard/new',
        component: SafeguardUpdateComponent,
        resolve: {
            safeguard: SafeguardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'safeguard/:id/edit',
        component: SafeguardUpdateComponent,
        resolve: {
            safeguard: SafeguardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const safeguardPopupRoute: Routes = [
    {
        path: 'safeguard/:id/delete',
        component: SafeguardDeletePopupComponent,
        resolve: {
            safeguard: SafeguardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.safeguard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
