import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAssetType } from 'app/shared/model/asset-type.model';

type EntityResponseType = HttpResponse<IAssetType>;
type EntityArrayResponseType = HttpResponse<IAssetType[]>;

@Injectable({ providedIn: 'root' })
export class AssetTypeService {
    private resourceUrl = SERVER_API_URL + 'api/asset-types';

    constructor(private http: HttpClient) {}

    create(assetType: IAssetType): Observable<EntityResponseType> {
        return this.http.post<IAssetType>(this.resourceUrl, assetType, { observe: 'response' });
    }

    update(assetType: IAssetType): Observable<EntityResponseType> {
        return this.http.put<IAssetType>(this.resourceUrl, assetType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAssetType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAssetType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
