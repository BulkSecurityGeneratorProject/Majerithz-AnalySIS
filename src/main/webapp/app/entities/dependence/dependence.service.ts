import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDependence } from 'app/shared/model/dependence.model';

type EntityResponseType = HttpResponse<IDependence>;
type EntityArrayResponseType = HttpResponse<IDependence[]>;

@Injectable({ providedIn: 'root' })
export class DependenceService {
    private resourceUrl = SERVER_API_URL + 'api/dependences';

    constructor(private http: HttpClient) {}

    create(dependence: IDependence): Observable<EntityResponseType> {
        return this.http.post<IDependence>(this.resourceUrl, dependence, { observe: 'response' });
    }

    update(dependence: IDependence): Observable<EntityResponseType> {
        return this.http.put<IDependence>(this.resourceUrl, dependence, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDependence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDependence[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
