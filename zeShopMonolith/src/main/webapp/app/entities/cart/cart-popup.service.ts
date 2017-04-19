import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Cart } from './cart.model';
import { CartService } from './cart.service';
@Injectable()
export class CartPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private cartService: CartService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.cartService.find(id).subscribe(cart => {
                cart.createdDate = this.datePipe
                    .transform(cart.createdDate, 'yyyy-MM-ddThh:mm');
                cart.updatedDate = this.datePipe
                    .transform(cart.updatedDate, 'yyyy-MM-ddThh:mm');
                this.cartModalRef(component, cart);
            });
        } else {
            return this.cartModalRef(component, new Cart());
        }
    }

    cartModalRef(component: Component, cart: Cart): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cart = cart;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
