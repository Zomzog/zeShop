import {Component, NgModule} from '@angular/core';
import {MdButtonModule} from '@angular/material';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'zadmin-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBar {

}

@NgModule({
  imports: [MdButtonModule, RouterModule],
  exports: [NavBar],
  declarations: [NavBar],
})
export class NavBarModule {}