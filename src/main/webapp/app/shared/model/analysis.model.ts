import { Moment } from 'moment';
import { IAsset } from 'app/shared/model//asset.model';
import { ICompany } from 'app/shared/model//company.model';

export interface IAnalysis {
    id?: number;
    identifier?: string;
    analysisCreationDate?: Moment;
    analysisLastEdit?: Moment;
    step?: string;
    assets?: IAsset[];
    company?: ICompany;
}

export class Analysis implements IAnalysis {
    constructor(
        public id?: number,
        public identifier?: string,
        public analysisCreationDate?: Moment,
        public analysisLastEdit?: Moment,
        public step?: string,
        public assets?: IAsset[],
        public company?: ICompany
    ) {}
}
