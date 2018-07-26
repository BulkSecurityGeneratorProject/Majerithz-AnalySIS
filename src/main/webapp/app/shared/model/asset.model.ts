import { IAssetSubType } from 'app/shared/model//asset-sub-type.model';
import { IDimension } from 'app/shared/model//dimension.model';
import { IExistingSafeguards } from 'app/shared/model//existing-safeguards.model';
import { IThreat } from 'app/shared/model//threat.model';
import { IDependence } from 'app/shared/model//dependence.model';
import { ISafeguardsPackage } from 'app/shared/model//safeguards-package.model';
import { ICompany } from 'app/shared/model//company.model';
import { IAnalysis } from 'app/shared/model//analysis.model';

export const enum AssetsTypeCode {
    ESSENTIAL = 'ESSENTIAL',
    ARCH = 'ARCH',
    D = 'D',
    K = 'K',
    S = 'S',
    SW = 'SW',
    HW = 'HW',
    COM = 'COM',
    MEDIA = 'MEDIA',
    AUX = 'AUX',
    L = 'L',
    P = 'P'
}

export interface IAsset {
    id?: number;
    assetTypeCode?: AssetsTypeCode;
    assetName?: string;
    descriptionAsset?: string;
    identifier?: string;
    assetLocation?: string;
    owner?: string;
    responsible?: string;
    quantity?: number;
    assetQualitativeValue?: number;
    assetQuantitativeValue?: number;
    potentialRisk?: number;
    estimatedRisk?: number;
    assetSubTypes?: IAssetSubType[];
    dimensions?: IDimension[];
    existingSafeguards?: IExistingSafeguards[];
    threats?: IThreat[];
    dependences?: IDependence[];
    safeguardsPackages?: ISafeguardsPackage[];
    company?: ICompany;
    analyses?: IAnalysis[];
}

export class Asset implements IAsset {
    constructor(
        public id?: number,
        public assetTypeCode?: AssetsTypeCode,
        public assetName?: string,
        public descriptionAsset?: string,
        public identifier?: string,
        public assetLocation?: string,
        public owner?: string,
        public responsible?: string,
        public quantity?: number,
        public assetQualitativeValue?: number,
        public assetQuantitativeValue?: number,
        public potentialRisk?: number,
        public estimatedRisk?: number,
        public assetSubTypes?: IAssetSubType[],
        public dimensions?: IDimension[],
        public existingSafeguards?: IExistingSafeguards[],
        public threats?: IThreat[],
        public dependences?: IDependence[],
        public safeguardsPackages?: ISafeguardsPackage[],
        public company?: ICompany,
        public analyses?: IAnalysis[]
    ) {}
}
