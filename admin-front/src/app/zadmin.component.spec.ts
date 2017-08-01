import { MockOauthService } from './shared/testing/mock-oauth.service';
import { OauthService } from './shared/oauth.service';
import { NavBarModule } from './core/navbar/navbar.component';
import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { MdButtonModule, MdCardModule, MdInputModule } from '@angular/material';

import { ZAdminComponent } from './zadmin.component';

// describe('ZAdmin', () => {
//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [
//         ZAdmin
//       ],
//       imports: [
//         RouterTestingModule,
//         NavBarModule,
//         MdCardModule,
//         MdInputModule,
//         MdButtonModule
//       ],
//       providers: [
//         { provide: OauthService, useValue: MockOauthService }
//       ]
//     }).compileComponents();
//   }));

//   it('should create the app', async(() => {
//     const fixture = TestBed.createComponent(ZAdmin);
//     // const app = fixture.debugElement.componentInstance;
//     //expect(app).toBeTruthy();
//   }));

//   // it(`shTypeError: Cannot create property '__creationTrace__' on string '__zone_symbol__optimizedZoneEventTask'

//   // it('should render title in a h1 tag', async(() => {
//   //   const fixture = TestBed.createComponent(ZAdmin);
//   //   fixture.detectChanges();
//   //   const compiled = fixture.debugElement.nativeElement;
//   //   //expect(compiled.querySelector('h1').textContent).toContain('Welcome to app!');
//   // }));
// });
