import { IThreatType } from 'app/shared/model//threat-type.model';

export const enum ThreatsTypeCode {
    N = 'N',
    I = 'I',
    E = 'E',
    A = 'A'
}

export interface IThreatSubType {
    id?: number;
    threatTypeCode?: ThreatsTypeCode;
    threatSubTypeCode?: string;
    threatSubTypeName?: string;
    threatSubTypeDescription?: string;
    threatType?: IThreatType;
}

export class ThreatSubType implements IThreatSubType {
    constructor(
        public id?: number,
        public threatTypeCode?: ThreatsTypeCode,
        public threatSubTypeCode?: string,
        public threatSubTypeName?: string,
        public threatSubTypeDescription?: string,
        public threatType?: IThreatType
    ) {}
}
