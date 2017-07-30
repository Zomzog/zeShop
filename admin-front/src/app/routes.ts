import {Routes} from '@angular/router';

import {HomeComponent} from './home/home.component';
import {SignInComponent} from './sign-in/sign-in.component';

export const ZADMIN_ROUTES: Routes = [
    {path: '', component: HomeComponent, pathMatch: 'full'},
    {path: 'signIn', component: SignInComponent},
]