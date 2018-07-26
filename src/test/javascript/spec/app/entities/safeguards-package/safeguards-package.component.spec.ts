/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardsPackageComponent } from 'app/entities/safeguards-package/safeguards-package.component';
import { SafeguardsPackageService } from 'app/entities/safeguards-package/safeguards-package.service';
import { SafeguardsPackage } from 'app/shared/model/safeguards-package.model';

describe('Component Tests', () => {
    describe('SafeguardsPackage Management Component', () => {
        let comp: SafeguardsPackageComponent;
        let fixture: ComponentFixture<SafeguardsPackageComponent>;
        let service: SafeguardsPackageService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardsPackageComponent],
                providers: []
            })
                .overrideTemplate(SafeguardsPackageComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardsPackageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardsPackageService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SafeguardsPackage(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.safeguardsPackages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
