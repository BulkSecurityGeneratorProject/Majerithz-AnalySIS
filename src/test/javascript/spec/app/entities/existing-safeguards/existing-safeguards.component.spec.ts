/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { ExistingSafeguardsComponent } from 'app/entities/existing-safeguards/existing-safeguards.component';
import { ExistingSafeguardsService } from 'app/entities/existing-safeguards/existing-safeguards.service';
import { ExistingSafeguards } from 'app/shared/model/existing-safeguards.model';

describe('Component Tests', () => {
    describe('ExistingSafeguards Management Component', () => {
        let comp: ExistingSafeguardsComponent;
        let fixture: ComponentFixture<ExistingSafeguardsComponent>;
        let service: ExistingSafeguardsService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [ExistingSafeguardsComponent],
                providers: []
            })
                .overrideTemplate(ExistingSafeguardsComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExistingSafeguardsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExistingSafeguardsService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ExistingSafeguards(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.existingSafeguards[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
