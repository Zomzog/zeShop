import { Observable } from 'rxjs/Rx';
import { Http } from '@angular/http';
import { OauthService } from './../oauth.service';

export class MockOauthService extends OauthService {

    isAuthenticatedMock = false;

    login(username: string, password: string) : Observable<boolean> {
        return new Observable();
    }


    logout(): void {
        this.isAuthenticatedMock = false;
    }

    isAuthenticated(): boolean {
        return this.isAuthenticatedMock;
    }
}