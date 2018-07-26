import { IAsset } from 'app/shared/model//asset.model';

export const enum DimensionsTypeCode {
    D = 'D',
    I = 'I',
    C = 'C',
    A = 'A',
    T = 'T'
}

export interface IDimension {
    id?: number;
    dimensionTypeCode?: DimensionsTypeCode;
    dimensionDimension?: string;
    dimensionDescription?: string;
    asset?: IAsset;
}

export class Dimension implements IDimension {
    constructor(
        public id?: number,
        public dimensionTypeCode?: DimensionsTypeCode,
        public dimensionDimension?: string,
        public dimensionDescription?: string,
        public asset?: IAsset
    ) {}
}
