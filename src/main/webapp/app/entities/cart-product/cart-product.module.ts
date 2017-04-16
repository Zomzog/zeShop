import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeShopSharedModule } from '../../shared';

import {
    CartProductService,
    CartProductPopupService,
    CartProductComponent,
    CartProductDetailComponent,
    CartProductDialogComponent,
    CartProductPopupComponent,
    CartProductDeletePopupComponent,
    CartProductDeleteDialogComponent,
    cartProductRoute,
    cartProductPopupRoute,
    CartProductResolvePagingParams,
} from './';

let ENTITY_STATES = [
    ...cartProductRoute,
    ...cartProductPopupRoute,
];

@NgModule({
    imports: [
        ZeShopSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CartProductComponent,
        CartProductDetailComponent,
        CartProductDialogComponent,
        CartProductDeleteDialogComponent,
        CartProductPopupComponent,
        CartProductDeletePopupComponent,
    ],
    entryComponents: [
        CartProductComponent,
        CartProductDialogComponent,
        CartProductPopupComponent,
        CartProductDeleteDialogComponent,
        CartProductDeletePopupComponent,
    ],
    providers: [
        CartProductService,
        CartProductPopupService,
        CartProductResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZeShopCartProductModule {}
