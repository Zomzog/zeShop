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
  let oauthService: OauthService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NavBarComponent],

      providers: [
        { provide: OauthService, useValue: MockOauthService }
      ]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavBarComponent);
    oauthService = fixture.debugElement.injector.get(OauthService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
  it('sign in must be show', () => {
    de = fixture.debugElement.query(By.css('#singIn'));
    expect(de).not.toBeNull();
  });
  it('sign in must be hide when user is logged in', () => { 
    oauthService.token = "pony";
    de = fixture.debugElement.query(By.css('#singIn'));
    expect(de).toBeUndefined();
  });
});
