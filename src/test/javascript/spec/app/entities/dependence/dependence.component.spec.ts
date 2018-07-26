/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { DependenceComponent } from 'app/entities/dependence/dependence.component';
import { DependenceService } from 'app/entities/dependence/dependence.service';
import { Dependence } from 'app/shared/model/dependence.model';

describe('Component Tests', () => {
    describe('Dependence Management Component', () => {
        let comp: DependenceComponent;
        let fixture: ComponentFixture<DependenceComponent>;
        let service: DependenceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [DependenceComponent],
                providers: []
            })
                .overrideTemplate(DependenceComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DependenceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DependenceService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Dependence(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.dependences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
