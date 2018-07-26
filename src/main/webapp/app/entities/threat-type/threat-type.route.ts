import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ThreatType } from 'app/shared/model/threat-type.model';
import { ThreatTypeService } from './threat-type.service';
import { ThreatTypeComponent } from './threat-type.component';
import { ThreatTypeDetailComponent } from './threat-type-detail.component';
import { ThreatTypeUpdateComponent } from './threat-type-update.component';
import { ThreatTypeDeletePopupComponent } from './threat-type-delete-dialog.component';
import { IThreatType } from 'app/shared/model/threat-type.model';

@Injectable({ providedIn: 'root' })
export class ThreatTypeResolve implements Resolve<IThreatType> {
    constructor(private service: ThreatTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((threatType: HttpResponse<ThreatType>) => threatType.body));
        }
        return of(new ThreatType());
    }
}

export const threatTypeRoute: Routes = [
    {
        path: 'threat-type',
        component: ThreatTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-type/:id/view',
        component: ThreatTypeDetailComponent,
        resolve: {
            threatType: ThreatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-type/new',
        component: ThreatTypeUpdateComponent,
        resolve: {
            threatType: ThreatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-type/:id/edit',
        component: ThreatTypeUpdateComponent,
        resolve: {
            threatType: ThreatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const threatTypePopupRoute: Routes = [
    {
        path: 'threat-type/:id/delete',
        component: ThreatTypeDeletePopupComponent,
        resolve: {
            threatType: ThreatTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
