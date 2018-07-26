import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IThreat } from 'app/shared/model/threat.model';

type EntityResponseType = HttpResponse<IThreat>;
type EntityArrayResponseType = HttpResponse<IThreat[]>;

@Injectable({ providedIn: 'root' })
export class ThreatService {
    private resourceUrl = SERVER_API_URL + 'api/threats';

    constructor(private http: HttpClient) {}

    create(threat: IThreat): Observable<EntityResponseType> {
        return this.http.post<IThreat>(this.resourceUrl, threat, { observe: 'response' });
    }

    update(threat: IThreat): Observable<EntityResponseType> {
        return this.http.put<IThreat>(this.resourceUrl, threat, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IThreat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IThreat[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
