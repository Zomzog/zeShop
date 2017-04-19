import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CartDetailComponent } from '../../../../../../main/webapp/app/entities/cart/cart-detail.component';
import { CartService } from '../../../../../../main/webapp/app/entities/cart/cart.service';
import { Cart } from '../../../../../../main/webapp/app/entities/cart/cart.model';

describe('Component Tests', () => {

    describe('Cart Management Detail Component', () => {
        let comp: CartDetailComponent;
        let fixture: ComponentFixture<CartDetailComponent>;
        let service: CartService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CartDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    CartService
                ]
            }).overrideComponent(CartDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Cart(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cart).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
