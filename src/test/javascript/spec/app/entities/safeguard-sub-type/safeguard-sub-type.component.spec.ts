/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardSubTypeComponent } from 'app/entities/safeguard-sub-type/safeguard-sub-type.component';
import { SafeguardSubTypeService } from 'app/entities/safeguard-sub-type/safeguard-sub-type.service';
import { SafeguardSubType } from 'app/shared/model/safeguard-sub-type.model';

describe('Component Tests', () => {
    describe('SafeguardSubType Management Component', () => {
        let comp: SafeguardSubTypeComponent;
        let fixture: ComponentFixture<SafeguardSubTypeComponent>;
        let service: SafeguardSubTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardSubTypeComponent],
                providers: []
            })
                .overrideTemplate(SafeguardSubTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardSubTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardSubTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SafeguardSubType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.safeguardSubTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
