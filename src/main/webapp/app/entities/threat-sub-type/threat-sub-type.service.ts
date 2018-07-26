import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IThreatSubType } from 'app/shared/model/threat-sub-type.model';

type EntityResponseType = HttpResponse<IThreatSubType>;
type EntityArrayResponseType = HttpResponse<IThreatSubType[]>;

@Injectable({ providedIn: 'root' })
export class ThreatSubTypeService {
    private resourceUrl = SERVER_API_URL + 'api/threat-sub-types';

    constructor(private http: HttpClient) {}

    create(threatSubType: IThreatSubType): Observable<EntityResponseType> {
        return this.http.post<IThreatSubType>(this.resourceUrl, threatSubType, { observe: 'response' });
    }

    update(threatSubType: IThreatSubType): Observable<EntityResponseType> {
        return this.http.put<IThreatSubType>(this.resourceUrl, threatSubType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IThreatSubType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IThreatSubType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
