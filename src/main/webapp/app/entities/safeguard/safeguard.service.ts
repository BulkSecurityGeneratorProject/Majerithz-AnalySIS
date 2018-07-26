import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISafeguard } from 'app/shared/model/safeguard.model';

type EntityResponseType = HttpResponse<ISafeguard>;
type EntityArrayResponseType = HttpResponse<ISafeguard[]>;

@Injectable({ providedIn: 'root' })
export class SafeguardService {
    private resourceUrl = SERVER_API_URL + 'api/safeguards';

    constructor(private http: HttpClient) {}

    create(safeguard: ISafeguard): Observable<EntityResponseType> {
        return this.http.post<ISafeguard>(this.resourceUrl, safeguard, { observe: 'response' });
    }

    update(safeguard: ISafeguard): Observable<EntityResponseType> {
        return this.http.put<ISafeguard>(this.resourceUrl, safeguard, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISafeguard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISafeguard[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
