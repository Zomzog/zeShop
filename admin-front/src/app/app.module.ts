import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MdInputModule, MdCardModule } from '@angular/material'
import { HttpModule } from '@angular/http';

import { FlexLayoutModule } from '@angular/flex-layout';

import { ZADMIN_ROUTES } from './routes';
import { ZAdmin } from './zadmin.component';
import { NavBarModule } from './core/navbar/navbar.component';
import { HomeModule } from './home/home.component';
import { SignIn } from './sign-in/sign-in.component';

import { OauthService } from './shared/oauth.service';

@NgModule({
  declarations: [
    ZAdmin, 
    SignIn
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(ZADMIN_ROUTES),
    NavBarModule,
    HomeModule,
    FlexLayoutModule,
    MdCardModule,
    MdInputModule
  ],
  providers: [OauthService],
  bootstrap: [ZAdmin]
})
export class AppModule { }
