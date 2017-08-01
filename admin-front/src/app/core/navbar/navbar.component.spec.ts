import { By } from '@angular/platform-browser';
import { MockOauthService } from './../../shared/testing/mock-oauth.service';
import { OauthService } from './../../shared/oauth.service';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { MdButtonModule } from '@angular/material';
import { RouterTestingModule } from '@angular/router/testing';
import { DebugElement }    from '@angular/core';

import { NavBarComponent } from './navbar.component';

describe('NavbarComponent', () => {
  let component: NavBarComponent;
  let fixture: ComponentFixture<NavBarComponent>;  
  let de:      DebugElement;
  let el:      HTMLElement;
  var isAuthenticated: any;
  let oauthServiceMock: any;

  beforeEach(async(() => {
    isAuthenticated = false;
    oauthServiceMock = {
      isAuthenticated: jasmine.createSpy('isAuthenticated')
      .and.callFake(function(){
        return isAuthenticated;
      })
    }
    TestBed.configureTestingModule({ 
      declarations: [NavBarComponent],

      providers: [
        { provide: OauthService, useValue: oauthServiceMock }
      ]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
  it('sign in must be show', () => {
    fixture.detectChanges();
    de = fixture.debugElement.query(By.css('#singIn'));
    expect(de === null).toBe(false);
  });
  it('sign in must be hide when user is logged in', () => { 
    isAuthenticated = true;
    fixture.detectChanges();
    de = fixture.debugElement.query(By.css('#singIn'));
    expect(de === null).toBe(true);
  });
});
