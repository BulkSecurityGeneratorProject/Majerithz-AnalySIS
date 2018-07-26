import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

type EntityResponseType = HttpResponse<ISafeguardSubType>;
type EntityArrayResponseType = HttpResponse<ISafeguardSubType[]>;

@Injectable({ providedIn: 'root' })
export class SafeguardSubTypeService {
    private resourceUrl = SERVER_API_URL + 'api/safeguard-sub-types';

    constructor(private http: HttpClient) {}

    create(safeguardSubType: ISafeguardSubType): Observable<EntityResponseType> {
        return this.http.post<ISafeguardSubType>(this.resourceUrl, safeguardSubType, { observe: 'response' });
    }

    update(safeguardSubType: ISafeguardSubType): Observable<EntityResponseType> {
        return this.http.put<ISafeguardSubType>(this.resourceUrl, safeguardSubType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISafeguardSubType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISafeguardSubType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
