import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Cart } from './cart.model';
import { CartService } from './cart.service';

@Component({
    selector: 'jhi-cart-detail',
    templateUrl: './cart-detail.component.html'
})
export class CartDetailComponent implements OnInit, OnDestroy {

    cart: Cart;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private cartService: CartService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['cart']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.cartService.find(id).subscribe(cart => {
            this.cart = cart;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
