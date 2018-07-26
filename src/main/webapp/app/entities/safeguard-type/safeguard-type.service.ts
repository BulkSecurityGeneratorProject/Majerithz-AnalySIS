import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISafeguardType } from 'app/shared/model/safeguard-type.model';

type EntityResponseType = HttpResponse<ISafeguardType>;
type EntityArrayResponseType = HttpResponse<ISafeguardType[]>;

@Injectable({ providedIn: 'root' })
export class SafeguardTypeService {
    private resourceUrl = SERVER_API_URL + 'api/safeguard-types';

    constructor(private http: HttpClient) {}

    create(safeguardType: ISafeguardType): Observable<EntityResponseType> {
        return this.http.post<ISafeguardType>(this.resourceUrl, safeguardType, { observe: 'response' });
    }

    update(safeguardType: ISafeguardType): Observable<EntityResponseType> {
        return this.http.put<ISafeguardType>(this.resourceUrl, safeguardType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISafeguardType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISafeguardType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
