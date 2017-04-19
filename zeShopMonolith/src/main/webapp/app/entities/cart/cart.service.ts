import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Cart } from './cart.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class CartService {

    private resourceUrl = 'api/carts';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(cart: Cart): Observable<Cart> {
        let copy: Cart = Object.assign({}, cart);
        copy.createdDate = this.dateUtils.toDate(cart.createdDate);
        copy.updatedDate = this.dateUtils.toDate(cart.updatedDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(cart: Cart): Observable<Cart> {
        let copy: Cart = Object.assign({}, cart);

        copy.createdDate = this.dateUtils.toDate(cart.createdDate);

        copy.updatedDate = this.dateUtils.toDate(cart.updatedDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Cart> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            let jsonResponse = res.json();
            jsonResponse.createdDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.createdDate);
            jsonResponse.updatedDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.updatedDate);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        let options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }


    private convertResponse(res: any): any {
        let jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].createdDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].createdDate);
            jsonResponse[i].updatedDate = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].updatedDate);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        let options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            let params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
