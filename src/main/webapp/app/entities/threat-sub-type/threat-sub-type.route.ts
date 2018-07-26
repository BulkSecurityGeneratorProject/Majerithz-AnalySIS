import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ThreatSubType } from 'app/shared/model/threat-sub-type.model';
import { ThreatSubTypeService } from './threat-sub-type.service';
import { ThreatSubTypeComponent } from './threat-sub-type.component';
import { ThreatSubTypeDetailComponent } from './threat-sub-type-detail.component';
import { ThreatSubTypeUpdateComponent } from './threat-sub-type-update.component';
import { ThreatSubTypeDeletePopupComponent } from './threat-sub-type-delete-dialog.component';
import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';

@Injectable({ providedIn: 'root' })
export class ThreatSubTypeResolve implements Resolve<IThreatSubType> {
    constructor(private service: ThreatSubTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((threatSubType: HttpResponse<ThreatSubType>) => threatSubType.body));
        }
        return of(new ThreatSubType());
    }
}

export const threatSubTypeRoute: Routes = [
    {
        path: 'threat-sub-type',
        component: ThreatSubTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-sub-type/:id/view',
        component: ThreatSubTypeDetailComponent,
        resolve: {
            threatSubType: ThreatSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-sub-type/new',
        component: ThreatSubTypeUpdateComponent,
        resolve: {
            threatSubType: ThreatSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat-sub-type/:id/edit',
        component: ThreatSubTypeUpdateComponent,
        resolve: {
            threatSubType: ThreatSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatSubType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const threatSubTypePopupRoute: Routes = [
    {
        path: 'threat-sub-type/:id/delete',
        component: ThreatSubTypeDeletePopupComponent,
        resolve: {
            threatSubType: ThreatSubTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threatSubType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
