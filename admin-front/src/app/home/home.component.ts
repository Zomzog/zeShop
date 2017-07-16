import {Component, NgModule, OnInit} from '@angular/core';
import {RouterModule} from '@angular/router';

@Component({
  selector: 'zadmin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class Home implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}

@NgModule({
  imports: [RouterModule],
  exports: [Home],
  declarations: [Home],
})
export class HomeModule { }