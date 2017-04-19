import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ZeShopProductModule } from './product/product.module';
import { ZeShopCartModule } from './cart/cart.module';
import { ZeShopCartProductModule } from './cart-product/cart-product.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ZeShopProductModule,
        ZeShopCartModule,
        ZeShopCartProductModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZeShopEntityModule {}
