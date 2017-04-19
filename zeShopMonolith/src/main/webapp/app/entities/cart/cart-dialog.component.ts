import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Cart } from './cart.model';
import { CartPopupService } from './cart-popup.service';
import { CartService } from './cart.service';
import { Product, ProductService } from '../product';
import { User, UserService } from '../../shared';
@Component({
    selector: 'jhi-cart-dialog',
    templateUrl: './cart-dialog.component.html'
})
export class CartDialogComponent implements OnInit {

    cart: Cart;
    authorities: any[];
    isSaving: boolean;

    products: Product[];

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private cartService: CartService,
        private productService: ProductService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['cart']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.productService.query().subscribe(
            (res: Response) => { this.products = res.json(); }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.cart.id !== undefined) {
            this.cartService.update(this.cart)
                .subscribe((res: Cart) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.cartService.create(this.cart)
                .subscribe((res: Cart) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Cart) {
        this.eventManager.broadcast({ name: 'cartListModification', content: 'OK'});
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

    trackProductById(index: number, item: Product) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-cart-popup',
    template: ''
})
export class CartPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cartPopupService: CartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.cartPopupService
                    .open(CartDialogComponent, params['id']);
            } else {
                this.modalRef = this.cartPopupService
                    .open(CartDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
