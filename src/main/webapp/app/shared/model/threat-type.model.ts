import { IThreatSubType } from 'app/shared/model//threat-sub-type.model';

export const enum ThreatsTypeCode {
    N = 'N',
    I = 'I',
    E = 'E',
    A = 'A'
}

export interface IThreatType {
    id?: number;
    threatTypeCode?: ThreatsTypeCode;
    threatTypeName?: string;
    threatTypeDescription?: string;
    threatSubTypes?: IThreatSubType[];
}

export class ThreatType implements IThreatType {
    constructor(
        public id?: number,
        public threatTypeCode?: ThreatsTypeCode,
        public threatTypeName?: string,
        public threatTypeDescription?: string,
        public threatSubTypes?: IThreatSubType[]
    ) {}
}
