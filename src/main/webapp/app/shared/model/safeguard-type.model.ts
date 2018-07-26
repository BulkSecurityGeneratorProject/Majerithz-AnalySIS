import { ISafeguardSubType } from 'app/shared/model//safeguard-sub-type.model';

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

export interface ISafeguardType {
    id?: number;
    safeguardsTypeCode?: SafeguardsTypeCode;
    safeguardTypeName?: string;
    safeguardTypeDescription?: string;
    safeguardSubTypes?: ISafeguardSubType[];
}

export class SafeguardType implements ISafeguardType {
    constructor(
        public id?: number,
        public safeguardsTypeCode?: SafeguardsTypeCode,
        public safeguardTypeName?: string,
        public safeguardTypeDescription?: string,
        public safeguardSubTypes?: ISafeguardSubType[]
    ) {}
}
