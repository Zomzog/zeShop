import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { Cart } from './cart.model';


@Injectable()
export class CartService {
    private cartUrl = '/api/cart-service/carts';

    public token: string;

    constructor(private http: HttpClient) {
        // set token if saved in local storage
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.token = currentUser && currentUser.token;
    }

    getAll() : Promise<Cart[]> {

        let headers = new HttpHeaders();
        headers.append("Authorization", "Basic " + btoa("pony:ponysecret"));
        headers.append("Content-Type", "application/x-www-form-urlencoded");

        return this.http.get(this.cartUrl, { headers: headers })
        
            .toPromise()
            .then(response => response as Cart[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('An error occurred', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}