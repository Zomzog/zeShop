import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CartComponent } from './cart.component';
import { CartDetailComponent } from './cart-detail.component';
import { CartPopupComponent } from './cart-dialog.component';
import { CartDeletePopupComponent } from './cart-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CartResolvePagingParams implements Resolve<any> {

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

export const cartRoute: Routes = [
  {
    path: 'cart',
    component: CartComponent,
    resolve: {
      'pagingParams': CartResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cart.home.title'
    }
  }, {
    path: 'cart/:id',
    component: CartDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cart.home.title'
    }
  }
];

export const cartPopupRoute: Routes = [
  {
    path: 'cart-new',
    component: CartPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cart.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cart/:id/edit',
    component: CartPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cart.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'cart/:id/delete',
    component: CartDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'zeShopApp.cart.home.title'
    },
    outlet: 'popup'
  }
];
