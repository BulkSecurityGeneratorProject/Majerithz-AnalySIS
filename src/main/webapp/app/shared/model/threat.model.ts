import { IThreatSubType } from 'app/shared/model//threat-sub-type.model';
import { IAsset } from 'app/shared/model//asset.model';

export const enum ThreatsTypeCode {
    N = 'N',
    I = 'I',
    E = 'E',
    A = 'A'
}

export interface IThreat {
    id?: number;
    threatTypeCode?: ThreatsTypeCode;
    threatName?: string;
    threatCommentary?: string;
    assetDegradation?: number;
    theoreticalLikelihood?: number;
    potentialImpact?: number;
    threatSubType?: IThreatSubType;
    asset?: IAsset;
}

export class Threat implements IThreat {
    constructor(
        public id?: number,
        public threatTypeCode?: ThreatsTypeCode,
        public threatName?: string,
        public threatCommentary?: string,
        public assetDegradation?: number,
        public theoreticalLikelihood?: number,
        public potentialImpact?: number,
        public threatSubType?: IThreatSubType,
        public asset?: IAsset
    ) {}
}
