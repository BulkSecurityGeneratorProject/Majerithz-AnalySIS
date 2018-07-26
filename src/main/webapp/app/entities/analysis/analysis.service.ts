import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnalysis } from 'app/shared/model/analysis.model';

type EntityResponseType = HttpResponse<IAnalysis>;
type EntityArrayResponseType = HttpResponse<IAnalysis[]>;

@Injectable({ providedIn: 'root' })
export class AnalysisService {
    private resourceUrl = SERVER_API_URL + 'api/analyses';

    constructor(private http: HttpClient) {}

    create(analysis: IAnalysis): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(analysis);
        return this.http
            .post<IAnalysis>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(analysis: IAnalysis): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(analysis);
        return this.http
            .put<IAnalysis>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAnalysis>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAnalysis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(analysis: IAnalysis): IAnalysis {
        const copy: IAnalysis = Object.assign({}, analysis, {
            analysisCreationDate:
                analysis.analysisCreationDate != null && analysis.analysisCreationDate.isValid()
                    ? analysis.analysisCreationDate.toJSON()
                    : null,
            analysisLastEdit:
                analysis.analysisLastEdit != null && analysis.analysisLastEdit.isValid() ? analysis.analysisLastEdit.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.analysisCreationDate = res.body.analysisCreationDate != null ? moment(res.body.analysisCreationDate) : null;
        res.body.analysisLastEdit = res.body.analysisLastEdit != null ? moment(res.body.analysisLastEdit) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((analysis: IAnalysis) => {
            analysis.analysisCreationDate = analysis.analysisCreationDate != null ? moment(analysis.analysisCreationDate) : null;
            analysis.analysisLastEdit = analysis.analysisLastEdit != null ? moment(analysis.analysisLastEdit) : null;
        });
        return res;
    }
}
