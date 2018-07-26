import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISafeguardsPackage } from 'app/shared/model/safeguards-package.model';

type EntityResponseType = HttpResponse<ISafeguardsPackage>;
type EntityArrayResponseType = HttpResponse<ISafeguardsPackage[]>;

@Injectable({ providedIn: 'root' })
export class SafeguardsPackageService {
    private resourceUrl = SERVER_API_URL + 'api/safeguards-packages';

    constructor(private http: HttpClient) {}

    create(safeguardsPackage: ISafeguardsPackage): Observable<EntityResponseType> {
        return this.http.post<ISafeguardsPackage>(this.resourceUrl, safeguardsPackage, { observe: 'response' });
    }

    update(safeguardsPackage: ISafeguardsPackage): Observable<EntityResponseType> {
        return this.http.put<ISafeguardsPackage>(this.resourceUrl, safeguardsPackage, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISafeguardsPackage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISafeguardsPackage[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
