import { OauthService } from './../../shared/oauth.service';
import {Component, NgModule} from '@angular/core';
import {MdButtonModule} from '@angular/material';
import {RouterModule} from '@angular/router';


@Component({
  selector: 'zadmin-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBar {
  constructor(private oauthService: OauthService) {};

  logout() {
    this.oauthService.logout();
  }
}

@NgModule({
  imports: [MdButtonModule, RouterModule],
  exports: [NavBar],
  declarations: [NavBar],
})
export class NavBarModule {}