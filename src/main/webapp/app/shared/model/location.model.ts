import { ICompany } from 'app/shared/model//company.model';

export interface ILocation {
    id?: number;
    locationDescription?: string;
    country?: string;
    streetAddress?: string;
    postalCode?: string;
    city?: string;
    stateProvince?: string;
    company?: ICompany;
}

export class Location implements ILocation {
    constructor(
        public id?: number,
        public locationDescription?: string,
        public country?: string,
        public streetAddress?: string,
        public postalCode?: string,
        public city?: string,
        public stateProvince?: string,
        public company?: ICompany
    ) {}
}
