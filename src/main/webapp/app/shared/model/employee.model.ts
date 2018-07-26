import { Moment } from 'moment';
import { ICompany } from 'app/shared/model//company.model';
import { IDepartment } from 'app/shared/model//department.model';

export interface IEmployee {
    id?: number;
    firstName?: string;
    lastName?: string;
    dni?: string;
    email?: string;
    phoneNumber?: string;
    birthday?: Moment;
    hireDate?: Moment;
    job?: string;
    task?: string;
    company?: ICompany;
    departments?: IDepartment[];
}

export class Employee implements IEmployee {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public dni?: string,
        public email?: string,
        public phoneNumber?: string,
        public birthday?: Moment,
        public hireDate?: Moment,
        public job?: string,
        public task?: string,
        public company?: ICompany,
        public departments?: IDepartment[]
    ) {}
}
