import { IAssetSubType } from 'app/shared/model//asset-sub-type.model';

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

export interface IAssetType {
    id?: number;
    assetTypeCode?: AssetsTypeCode;
    assetTypeName?: string;
    assetTypeDescription?: string;
    assetSubTypes?: IAssetSubType[];
}

export class AssetType implements IAssetType {
    constructor(
        public id?: number,
        public assetTypeCode?: AssetsTypeCode,
        public assetTypeName?: string,
        public assetTypeDescription?: string,
        public assetSubTypes?: IAssetSubType[]
    ) {}
}
