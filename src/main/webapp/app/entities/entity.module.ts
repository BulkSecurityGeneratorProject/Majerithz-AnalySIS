import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MajerithzAnalySisLocationModule } from './location/location.module';
import { MajerithzAnalySisDepartmentModule } from './department/department.module';
import { MajerithzAnalySisEmployeeModule } from './employee/employee.module';
import { MajerithzAnalySisCompanyModule } from './company/company.module';
import { MajerithzAnalySisAnalysisModule } from './analysis/analysis.module';
import { MajerithzAnalySisAssetModule } from './asset/asset.module';
import { MajerithzAnalySisAssetTypeModule } from './asset-type/asset-type.module';
import { MajerithzAnalySisAssetSubTypeModule } from './asset-sub-type/asset-sub-type.module';
import { MajerithzAnalySisDependenceModule } from './dependence/dependence.module';
import { MajerithzAnalySisDimensionModule } from './dimension/dimension.module';
import { MajerithzAnalySisThreatTypeModule } from './threat-type/threat-type.module';
import { MajerithzAnalySisThreatSubTypeModule } from './threat-sub-type/threat-sub-type.module';
import { MajerithzAnalySisThreatModule } from './threat/threat.module';
import { MajerithzAnalySisSafeguardTypeModule } from './safeguard-type/safeguard-type.module';
import { MajerithzAnalySisSafeguardSubTypeModule } from './safeguard-sub-type/safeguard-sub-type.module';
import { MajerithzAnalySisExistingSafeguardsModule } from './existing-safeguards/existing-safeguards.module';
import { MajerithzAnalySisSafeguardModule } from './safeguard/safeguard.module';
import { MajerithzAnalySisSafeguardsPackageModule } from './safeguards-package/safeguards-package.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MajerithzAnalySisLocationModule,
        MajerithzAnalySisDepartmentModule,
        MajerithzAnalySisEmployeeModule,
        MajerithzAnalySisCompanyModule,
        MajerithzAnalySisAnalysisModule,
        MajerithzAnalySisAssetModule,
        MajerithzAnalySisAssetTypeModule,
        MajerithzAnalySisAssetSubTypeModule,
        MajerithzAnalySisDependenceModule,
        MajerithzAnalySisDimensionModule,
        MajerithzAnalySisThreatTypeModule,
        MajerithzAnalySisThreatSubTypeModule,
        MajerithzAnalySisThreatModule,
        MajerithzAnalySisSafeguardTypeModule,
        MajerithzAnalySisSafeguardSubTypeModule,
        MajerithzAnalySisExistingSafeguardsModule,
        MajerithzAnalySisSafeguardModule,
        MajerithzAnalySisSafeguardsPackageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MajerithzAnalySisEntityModule {}
