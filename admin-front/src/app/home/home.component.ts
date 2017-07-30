import { Cart } from './../shared/cart.model';
import { CartService } from './../shared/cartservice';
import {Component, NgModule, OnInit} from '@angular/core';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'zadmin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  carts : Cart[];
  constructor(private cartService: CartService) { }

  ngOnInit() {
      this.cartService.getAll().then(carts => this.carts = carts);
  }

}

@NgModule({
  imports: [RouterModule],
  exports: [HomeComponent],
  declarations: [HomeComponent],
})
export class HomeModule { }