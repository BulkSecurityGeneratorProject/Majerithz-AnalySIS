/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardTypeComponent } from 'app/entities/safeguard-type/safeguard-type.component';
import { SafeguardTypeService } from 'app/entities/safeguard-type/safeguard-type.service';
import { SafeguardType } from 'app/shared/model/safeguard-type.model';

describe('Component Tests', () => {
    describe('SafeguardType Management Component', () => {
        let comp: SafeguardTypeComponent;
        let fixture: ComponentFixture<SafeguardTypeComponent>;
        let service: SafeguardTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardTypeComponent],
                providers: []
            })
                .overrideTemplate(SafeguardTypeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardTypeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SafeguardType(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.safeguardTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
