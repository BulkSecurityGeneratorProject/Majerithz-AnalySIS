import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IThreatType } from 'app/shared/model/threat-type.model';

type EntityResponseType = HttpResponse<IThreatType>;
type EntityArrayResponseType = HttpResponse<IThreatType[]>;

@Injectable({ providedIn: 'root' })
export class ThreatTypeService {
    private resourceUrl = SERVER_API_URL + 'api/threat-types';

    constructor(private http: HttpClient) {}

    create(threatType: IThreatType): Observable<EntityResponseType> {
        return this.http.post<IThreatType>(this.resourceUrl, threatType, { observe: 'response' });
    }

    update(threatType: IThreatType): Observable<EntityResponseType> {
        return this.http.put<IThreatType>(this.resourceUrl, threatType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IThreatType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IThreatType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
