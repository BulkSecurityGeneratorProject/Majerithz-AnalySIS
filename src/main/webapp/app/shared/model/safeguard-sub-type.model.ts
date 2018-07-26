import { ISafeguardType } from 'app/shared/model//safeguard-type.model';

export const enum SafeguardsTypeCode {
    H = 'H',
    D = 'D',
    K = 'K',
    S = 'S',
    SW = 'SW',
    HW = 'HW',
    COM = 'COM',
    IP = 'IP',
    MP = 'MP',
    AUX = 'AUX',
    L = 'L',
    PS = 'PS',
    G = 'G',
    BC = 'BC',
    E = 'E',
    NEW = 'NEW'
}

export interface ISafeguardSubType {
    id?: number;
    safeguardsTypeCode?: SafeguardsTypeCode;
    codeSafeguardSubType?: string;
    safeguardSubTypeName?: string;
    safeguardSubTypeDescription?: string;
    safeguardType?: ISafeguardType;
}

export class SafeguardSubType implements ISafeguardSubType {
    constructor(
        public id?: number,
        public safeguardsTypeCode?: SafeguardsTypeCode,
        public codeSafeguardSubType?: string,
        public safeguardSubTypeName?: string,
        public safeguardSubTypeDescription?: string,
        public safeguardType?: ISafeguardType
    ) {}
}
