import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CartProduct } from './cart-product.model';
import { CartProductService } from './cart-product.service';

@Component({
    selector: 'jhi-cart-product-detail',
    templateUrl: './cart-product-detail.component.html'
})
export class CartProductDetailComponent implements OnInit, OnDestroy {

    cartProduct: CartProduct;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private cartProductService: CartProductService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['cartProduct']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.cartProductService.find(id).subscribe(cartProduct => {
            this.cartProduct = cartProduct;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
