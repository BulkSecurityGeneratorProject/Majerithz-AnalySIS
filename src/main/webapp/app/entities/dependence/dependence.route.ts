import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Dependence } from 'app/shared/model/dependence.model';
import { DependenceService } from './dependence.service';
import { DependenceComponent } from './dependence.component';
import { DependenceDetailComponent } from './dependence-detail.component';
import { DependenceUpdateComponent } from './dependence-update.component';
import { DependenceDeletePopupComponent } from './dependence-delete-dialog.component';
import { IDependence } from 'app/shared/model/dependence.model';

@Injectable({ providedIn: 'root' })
export class DependenceResolve implements Resolve<IDependence> {
    constructor(private service: DependenceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((dependence: HttpResponse<Dependence>) => dependence.body));
        }
        return of(new Dependence());
    }
}

export const dependenceRoute: Routes = [
    {
        path: 'dependence',
        component: DependenceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.dependence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependence/:id/view',
        component: DependenceDetailComponent,
        resolve: {
            dependence: DependenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.dependence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependence/new',
        component: DependenceUpdateComponent,
        resolve: {
            dependence: DependenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.dependence.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'dependence/:id/edit',
        component: DependenceUpdateComponent,
        resolve: {
            dependence: DependenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.dependence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dependencePopupRoute: Routes = [
    {
        path: 'dependence/:id/delete',
        component: DependenceDeletePopupComponent,
        resolve: {
            dependence: DependenceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'majerithzAnalySisApp.dependence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
