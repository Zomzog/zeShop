import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CartProductComponent } from './cart-product.component';
import { CartProductDetailComponent } from './cart-product-detail.component';
import { CartProductPopupComponent } from './cart-product-dialog.component';
import { CartProductDeletePopupComponent } from './cart-product-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CartProductResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const cartProductRoute: Routes = [
  {
    path: 'cart-product',
    component: CartProductComponent,
    resolve: {
      'pagingParams': CartProductResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cartProduct.home.title'
    }
  }, {
    path: 'cart-product/:id',
    component: CartProductDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cartProduct.home.title'
    }
  }
];

export const cartProductPopupRoute: Routes = [
  {
    path: 'cart-product-new',
    component: CartProductPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cartProduct.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cart-product/:id/edit',
    component: CartProductPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cartProduct.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cart-product/:id/delete',
    component: CartProductDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cartProduct.home.title'
    },
    outlet: 'popup'
  }
];
