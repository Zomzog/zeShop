import { Observable } from 'rxjs/Rx';
import {Injectable} from '@angular/core';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest} from '@angular/common/http';

import { OauthService } from './oauth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private auth: OauthService) {
    }


  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const authReq = req.clone({setHeaders: {Authorization: this.auth.token}});
    return next.handle(authReq);
  }
}