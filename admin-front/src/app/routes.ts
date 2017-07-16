import {Routes} from '@angular/router';

import {Home} from './home/home.component';
import {SignIn} from './sign-in/sign-in.component';

export const ZADMIN_ROUTES: Routes = [
    {path: '', component: Home, pathMatch: 'full'},
    {path: 'signIn', component: SignIn},
]