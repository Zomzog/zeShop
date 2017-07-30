import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class OauthService {
    private uuaUrl = '/api/auth-service/oauth/token';

    public token: string;

    constructor(private http: Http) {
        // set token if saved in local storage
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.token = currentUser && currentUser.token;
    }

    login(username: string, password: string) : Observable<boolean> {

        let headers = new Headers();
        headers.append("Authorization", "Basic " + btoa("pony:ponysecret"));
        headers.append("Content-Type", "application/x-www-form-urlencoded");

        return this.http.post(this.uuaUrl,
            'username=' + username +
            '&password=' + password +
            '&grant_type=password'
            , { headers: headers })
            .map(response => {
                // login successful if there's a jwt token in the response
                let token = response.json() && response.json().access_token;
                if (token) {
                    let btoken = 'Bearer ' + token;
                    // set token property
                    this.token = btoken;

                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser', JSON.stringify({ username: username, token: btoken }));

                    // return true to indicate successful login
                    return true;
                } else {
                    // return false to indicate failed login
                    return false;
                }
            });
    }


    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
        localStorage.removeItem('currentUser');
    }

    isAuthenticated(): boolean {
        return this.token != null;
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}