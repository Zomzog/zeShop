import {Component, NgModule} from '@angular/core';
import {MdButtonModule} from '@angular/material';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.sass']
})
export class NavBar {

}

@NgModule({
  imports: [MdButtonModule, RouterModule],
  exports: [NavBar],
  declarations: [NavBar],
})
export class NavBarModule {}