import { Component, OnInit, NgModule } from '@angular/core';
import { OauthService } from '../shared/oauth.service'
import { Router } from '@angular/router';

@Component({
  selector: 'zadmin-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent implements OnInit {

  error = "";
  loading = false;
  auth = {
    login: "",
    password: ""
  };

  constructor(
    private router: Router,
    private oauthService: OauthService) { }

  ngOnInit() {
  }

  login(): void {

    this.loading = true;
    this.oauthService.login(this.auth.login, this.auth.password)
      .subscribe(result => {
        if (result === true) {
          // login successful
          this.router.navigate(['/']);
        } else {
          // login failed
          this.error = 'Username or password is incorrect';
          this.loading = false;
        }
      });
  }
}
