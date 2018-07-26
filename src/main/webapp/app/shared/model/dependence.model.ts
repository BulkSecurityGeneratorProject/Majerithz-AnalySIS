import { IAsset } from 'app/shared/model//asset.model';

export interface IDependence {
    id?: number;
    degree?: number;
    reason?: string;
    asset?: IAsset;
}

export class Dependence implements IDependence {
    constructor(public id?: number, public degree?: number, public reason?: string, public asset?: IAsset) {}
}
