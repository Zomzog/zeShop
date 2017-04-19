import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { CartProduct } from './cart-product.model';
import { CartProductPopupService } from './cart-product-popup.service';
import { CartProductService } from './cart-product.service';

@Component({
    selector: 'jhi-cart-product-delete-dialog',
    templateUrl: './cart-product-delete-dialog.component.html'
})
export class CartProductDeleteDialogComponent {

    cartProduct: CartProduct;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private cartProductService: CartProductService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['cartProduct']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.cartProductService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cartProductListModification',
                content: 'Deleted an cartProduct'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cart-product-delete-popup',
    template: ''
})
export class CartProductDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cartProductPopupService: CartProductPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.cartProductPopupService
                .open(CartProductDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
