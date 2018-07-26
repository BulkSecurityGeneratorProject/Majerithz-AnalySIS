import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

type EntityResponseType = HttpResponse<IExistingSafeguards>;
type EntityArrayResponseType = HttpResponse<IExistingSafeguards[]>;

@Injectable({ providedIn: 'root' })
export class ExistingSafeguardsService {
    private resourceUrl = SERVER_API_URL + 'api/existing-safeguards';

    constructor(private http: HttpClient) {}

    create(existingSafeguards: IExistingSafeguards): Observable<EntityResponseType> {
        return this.http.post<IExistingSafeguards>(this.resourceUrl, existingSafeguards, { observe: 'response' });
    }

    update(existingSafeguards: IExistingSafeguards): Observable<EntityResponseType> {
        return this.http.put<IExistingSafeguards>(this.resourceUrl, existingSafeguards, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IExistingSafeguards>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IExistingSafeguards[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
