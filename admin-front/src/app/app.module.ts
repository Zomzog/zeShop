import { HttpModule } from '@angular/http';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MdInputModule, MdCardModule } from '@angular/material'
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';

import { FlexLayoutModule } from '@angular/flex-layout';

import { ZADMIN_ROUTES } from './routes';
import { ZAdminComponent } from './zadmin.component';
import { NavBarModule } from './core/navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { SignInComponent } from './sign-in/sign-in.component';

import { OauthService } from './shared/oauth.service';
import { CartService } from './shared/cartservice';
import { AuthInterceptor } from './shared/auth.interceptor';

@NgModule({
  declarations: [
    ZAdminComponent, 
    SignInComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(ZADMIN_ROUTES),
    NavBarModule,
    FlexLayoutModule,
    MdCardModule,
    MdInputModule,
    HttpModule
  ],
  providers: [
    OauthService,
    CartService,
    AuthInterceptor,
    {   
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    }],
  bootstrap: [ZAdminComponent]
})
export class AppModule { }
