/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MajerithzAnalySisTestModule } from '../../../test.module';
import { SafeguardComponent } from 'app/entities/safeguard/safeguard.component';
import { SafeguardService } from 'app/entities/safeguard/safeguard.service';
import { Safeguard } from 'app/shared/model/safeguard.model';

describe('Component Tests', () => {
    describe('Safeguard Management Component', () => {
        let comp: SafeguardComponent;
        let fixture: ComponentFixture<SafeguardComponent>;
        let service: SafeguardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MajerithzAnalySisTestModule],
                declarations: [SafeguardComponent],
                providers: []
            })
                .overrideTemplate(SafeguardComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SafeguardComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SafeguardService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Safeguard(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.safeguards[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
