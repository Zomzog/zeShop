import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CartProduct } from './cart-product.model';
import { CartProductService } from './cart-product.service';
@Injectable()
export class CartProductPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private cartProductService: CartProductService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.cartProductService.find(id).subscribe(cartProduct => {
                cartProduct.addedDate = this.datePipe
                    .transform(cartProduct.addedDate, 'yyyy-MM-ddThh:mm');
                this.cartProductModalRef(component, cartProduct);
            });
        } else {
            return this.cartProductModalRef(component, new CartProduct());
        }
    }

    cartProductModalRef(component: Component, cartProduct: CartProduct): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.cartProduct = cartProduct;
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
