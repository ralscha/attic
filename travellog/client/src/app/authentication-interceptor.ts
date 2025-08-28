import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {from, Observable, throwError} from 'rxjs';
import {AppDatabase} from './model/app-database';
import {catchError, mergeMap} from 'rxjs/operators';
import {Router} from '@angular/router';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  private readonly ignoreURLs = new Set(['/be/login', '/be/signup', '/be/confirm-signup',
    '/be/reset-password-request', '/be/reset-password', '/be/confirm-email-change']);

  constructor(private readonly appDatabase: AppDatabase,
              private readonly router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.ignoreURLs.has(request.url)) {
      return next.handle(request);
    }

    const tokenGetter = async () => {
      const token = sessionStorage.getItem('token');
      if (token) {
        return token;
      }
      return this.appDatabase.authenticationToken.limit(1).first();
    };

    // @ts-ignore
    return from(tokenGetter()).pipe(mergeMap(
      (token: string) => {

        let nextHandle: Observable<HttpEvent<any>>;

        if (token) {
          const clonedRequest = request.clone({
            setHeaders: {
              'X-authentication': token
            }
          });

          nextHandle = next.handle(clonedRequest);
        } else {
          nextHandle = next.handle(request);
        }

        if (request.url.endsWith('/authenticate')) {
          return nextHandle;
        }

        return nextHandle.pipe(catchError(err => {
          if (err.status === 401) {
            this.appDatabase.authenticationToken.clear();
            this.router.navigate(['/login']);
          }
          return throwError((err.error && err.error.message) || err.statusText);
        }));
      }
    ));

  }
}
