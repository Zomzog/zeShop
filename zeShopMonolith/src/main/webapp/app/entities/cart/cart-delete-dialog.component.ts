import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Cart } from './cart.model';
import { CartPopupService } from './cart-popup.service';
import { CartService } from './cart.service';

@Component({
    selector: 'jhi-cart-delete-dialog',
    templateUrl: './cart-delete-dialog.component.html'
})
export class CartDeleteDialogComponent {

    cart: Cart;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private cartService: CartService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['cart']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.cartService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cartListModification',
                content: 'Deleted an cart'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cart-delete-popup',
    template: ''
})
export class CartDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private cartPopupService: CartPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.cartPopupService
                .open(CartDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
