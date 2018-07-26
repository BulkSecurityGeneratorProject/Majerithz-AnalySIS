import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Threat } from 'app/shared/model/threat.model';
import { ThreatService } from './threat.service';
import { ThreatComponent } from './threat.component';
import { ThreatDetailComponent } from './threat-detail.component';
import { ThreatUpdateComponent } from './threat-update.component';
import { ThreatDeletePopupComponent } from './threat-delete-dialog.component';
import { IThreat } from 'app/shared/model/threat.model';

@Injectable({ providedIn: 'root' })
export class ThreatResolve implements Resolve<IThreat> {
    constructor(private service: ThreatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((threat: HttpResponse<Threat>) => threat.body));
        }
        return of(new Threat());
    }
}

export const threatRoute: Routes = [
    {
        path: 'threat',
        component: ThreatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat/:id/view',
        component: ThreatDetailComponent,
        resolve: {
            threat: ThreatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat/new',
        component: ThreatUpdateComponent,
        resolve: {
            threat: ThreatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'threat/:id/edit',
        component: ThreatUpdateComponent,
        resolve: {
            threat: ThreatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const threatPopupRoute: Routes = [
    {
        path: 'threat/:id/delete',
        component: ThreatDeletePopupComponent,
        resolve: {
            threat: ThreatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.threat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
