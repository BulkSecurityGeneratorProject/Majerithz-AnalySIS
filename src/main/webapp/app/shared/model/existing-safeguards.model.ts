import { IAsset } from 'app/shared/model//asset.model';
import { ISafeguard } from 'app/shared/model//safeguard.model';

export interface IExistingSafeguards {
    id?: number;
    existingSafeguardsName?: string;
    existingSafeguardsCommentary?: string;
    effectivenessAgainstDegradation?: number;
    effectivenessAgainstLikelihood?: number;
    asset?: IAsset;
    safeguard?: ISafeguard;
}

export class ExistingSafeguards implements IExistingSafeguards {
    constructor(
        public id?: number,
        public existingSafeguardsName?: string,
        public existingSafeguardsCommentary?: string,
        public effectivenessAgainstDegradation?: number,
        public effectivenessAgainstLikelihood?: number,
        public asset?: IAsset,
        public safeguard?: ISafeguard
    ) {}
}
