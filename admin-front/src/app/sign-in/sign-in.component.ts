import { Component, OnInit, NgModule } from '@angular/core';
import { OauthService } from '../shared/oauth.service'

@Component({
  selector: 'zadmin-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignIn implements OnInit {

  auth = {
    login: "",
    password: ""
  };

  constructor(private oauthService: OauthService) { }

  ngOnInit() {
  }

  login(): void {
    this.oauthService.login(this.auth.login, this.auth.password);
  }
}
