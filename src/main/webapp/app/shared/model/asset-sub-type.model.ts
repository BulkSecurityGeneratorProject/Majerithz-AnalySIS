import { IAsset } from 'app/shared/model//asset.model';
import { IAssetType } from 'app/shared/model//asset-type.model';

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

export interface IAssetSubType {
    id?: number;
    assetTypeCode?: AssetsTypeCode;
    assetSubTypeCode?: string;
    assetSubTypeName?: string;
    assetSubTypeDescription?: string;
    asset?: IAsset;
    assetType?: IAssetType;
}

export class AssetSubType implements IAssetSubType {
    constructor(
        public id?: number,
        public assetTypeCode?: AssetsTypeCode,
        public assetSubTypeCode?: string,
        public assetSubTypeName?: string,
        public assetSubTypeDescription?: string,
        public asset?: IAsset,
        public assetType?: IAssetType
    ) {}
}
