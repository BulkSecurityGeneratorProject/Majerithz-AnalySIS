import { Moment } from 'moment';
import { ILocation } from 'app/shared/model//location.model';
import { IEmployee } from 'app/shared/model//employee.model';
import { IAnalysis } from 'app/shared/model//analysis.model';
import { IAsset } from 'app/shared/model//asset.model';

export interface ICompany {
    id?: number;
    companyName?: string;
    companyIdentifier?: string;
    creationDate?: Moment;
    sector?: string;
    assumedRisk?: number;
    locations?: ILocation[];
    employees?: IEmployee[];
    analyses?: IAnalysis[];
    assets?: IAsset[];
}

export class Company implements ICompany {
    constructor(
        public id?: number,
        public companyName?: string,
        public companyIdentifier?: string,
        public creationDate?: Moment,
        public sector?: string,
        public assumedRisk?: number,
        public locations?: ILocation[],
        public employees?: IEmployee[],
        public analyses?: IAnalysis[],
        public assets?: IAsset[]
    ) {}
}
