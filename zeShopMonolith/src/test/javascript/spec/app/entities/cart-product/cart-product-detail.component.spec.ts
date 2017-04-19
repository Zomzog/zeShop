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
import { CartProductDetailComponent } from '../../../../../../main/webapp/app/entities/cart-product/cart-product-detail.component';
import { CartProductService } from '../../../../../../main/webapp/app/entities/cart-product/cart-product.service';
import { CartProduct } from '../../../../../../main/webapp/app/entities/cart-product/cart-product.model';

describe('Component Tests', () => {

    describe('CartProduct Management Detail Component', () => {
        let comp: CartProductDetailComponent;
        let fixture: ComponentFixture<CartProductDetailComponent>;
        let service: CartProductService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CartProductDetailComponent],
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
                    CartProductService
                ]
            }).overrideComponent(CartProductDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CartProductDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CartProductService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CartProduct(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.cartProduct).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
