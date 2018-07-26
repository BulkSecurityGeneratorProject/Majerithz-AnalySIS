import { IAsset } from 'app/shared/model//asset.model';
import { ISafeguard } from 'app/shared/model//safeguard.model';

export interface ISafeguardsPackage {
    id?: number;
    safeguardsPackageName?: string;
    safeguardsPackageCommentary?: string;
    effectivenessAgainstDegradation?: number;
    effectivenessAgainstLikelihood?: number;
    asset?: IAsset;
    safeguard?: ISafeguard;
}

export class SafeguardsPackage implements ISafeguardsPackage {
    constructor(
        public id?: number,
        public safeguardsPackageName?: string,
        public safeguardsPackageCommentary?: string,
        public effectivenessAgainstDegradation?: number,
        public effectivenessAgainstLikelihood?: number,
        public asset?: IAsset,
        public safeguard?: ISafeguard
    ) {}
}
