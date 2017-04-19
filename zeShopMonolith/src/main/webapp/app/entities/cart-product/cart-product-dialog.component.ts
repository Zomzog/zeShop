import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CartProduct } from './cart-product.model';
import { CartProductPopupService } from './cart-product-popup.service';
import { CartProductService } from './cart-product.service';
import { Cart, CartService } from '../cart';
import { Product, ProductService } from '../product';
import { ProductCustomization, ProductCustomizationService } from '../product-customization';
@Component({
    selector: 'jhi-cart-product-dialog',
    templateUrl: './cart-product-dialog.component.html'
})
export class CartProductDialogComponent implements OnInit {

    cartProduct: CartProduct;
    authorities: any[];
    isSaving: boolean;

    carts: Cart[];

    products: Product[];

    productcustomizations: ProductCustomization[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private cartProductService: CartProductService,
        private cartService: CartService,
        private productService: ProductService,
        private productCustomizationService: ProductCustomizationService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['cartProduct']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.cartService.query().subscribe(
            (res: Response) => { this.carts = res.json(); }, (res: Response) => this.onError(res.json()));
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
        this.productCustomizationService.query({filter: 'product-is-null'}).subscribe((res: Response) => {
            if (!this.cartProduct.productCustomizationId) {
                this.productcustomizations = res.json();
            } else {
                this.productCustomizationService.find(this.cartProduct.productCustomizationId).subscribe((subRes: ProductCustomization) => {
                    this.productcustomizations = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.cartProduct.id !== undefined) {
            this.cartProductService.update(this.cartProduct)
                .subscribe((res: CartProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.cartProductService.create(this.cartProduct)
                .subscribe((res: CartProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CartProduct) {
        this.eventManager.broadcast({ name: 'cartProductListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackCartById(index: number, item: Cart) {
        return item.id;
    }

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackProductCustomizationById(index: number, item: ProductCustomization) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cart-product-popup',
    template: ''
})
export class CartProductPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cartProductPopupService: CartProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.cartProductPopupService
                    .open(CartProductDialogComponent, params['id']);
            } else {
                this.modalRef = this.cartProductPopupService
                    .open(CartProductDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
