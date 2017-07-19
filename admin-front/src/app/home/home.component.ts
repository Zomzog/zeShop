import { Cart } from './../shared/cart.model';
import { CartService } from './../shared/cartservice';
import {Component, NgModule, OnInit} from '@angular/core';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'zadmin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class Home implements OnInit {

  carts : Cart[];
  constructor(private cartService: CartService) { }

  ngOnInit() {
      this.cartService.getAll().then(carts => this.carts = carts);
  }

}

@NgModule({
  imports: [RouterModule],
  exports: [Home],
  declarations: [Home],
})
export class HomeModule { }