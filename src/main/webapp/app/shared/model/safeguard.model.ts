import { ISafeguardSubType } from 'app/shared/model//safeguard-sub-type.model';
import { ISafeguardsPackage } from 'app/shared/model//safeguards-package.model';
import { IExistingSafeguards } from 'app/shared/model//existing-safeguards.model';

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

export interface ISafeguard {
    id?: number;
    safeguardsTypeCode?: SafeguardsTypeCode;
    safeguardName?: string;
    safeguardCommentary?: string;
    effectivenessAgainstDegradation?: number;
    effectivenessAgainstLikelihood?: number;
    safeguardSubType?: ISafeguardSubType;
    safeguardsPackages?: ISafeguardsPackage[];
    existingSafeguards?: IExistingSafeguards[];
}

export class Safeguard implements ISafeguard {
    constructor(
        public id?: number,
        public safeguardsTypeCode?: SafeguardsTypeCode,
        public safeguardName?: string,
        public safeguardCommentary?: string,
        public effectivenessAgainstDegradation?: number,
        public effectivenessAgainstLikelihood?: number,
        public safeguardSubType?: ISafeguardSubType,
        public safeguardsPackages?: ISafeguardsPackage[],
        public existingSafeguards?: IExistingSafeguards[]
    ) {}
}
