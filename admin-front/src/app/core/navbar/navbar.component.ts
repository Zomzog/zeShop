import { BrowserModule } from '@angular/platform-browser';
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
    return this.oauthService.isAuthenticated();
  }
  logout() {
    this.oauthService.logout();
  }
}

@NgModule({
  imports: [BrowserModule, MdButtonModule, RouterModule],
  exports: [NavBarComponent],
  declarations: [NavBarComponent],
})
export class NavBarModule { }