import { OauthService } from './../../shared/oauth.service';
import { Component, NgModule } from '@angular/core';
import { MdButtonModule } from '@angular/material';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'zadmin-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBarComponent {
  constructor(private oauthService: OauthService) { };

  isAuthenticated(): boolean {
    //return this.oauthService.isAuthenticated();
    return false;
  }
  logout() {
    this.oauthService.logout();
  }
}

@NgModule({
  imports: [MdButtonModule, RouterModule],
  exports: [NavBarComponent],
  declarations: [NavBarComponent],
})
export class NavBarModule { }