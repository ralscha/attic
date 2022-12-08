import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {catchError, mapTo, share, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly authenticationSubject = new BehaviorSubject<boolean>(false);
  readonly authentication$ = this.authenticationSubject.asObservable();
  private readonly authenticationCall$: Observable<boolean>;

  constructor(private readonly httpClient: HttpClient) {
    this.authenticationCall$ = this.httpClient.get<boolean>('authenticate', {
      withCredentials: true
    })
      .pipe(
        tap(() => this.authenticationSubject.next(true)),
        mapTo(true),
        catchError(error => {
          this.authenticationSubject.next(false);
          return of(false);
        }),
        share()
      );

  }

  authenticate(): Observable<boolean> {
    return this.authenticationCall$;
  }

  isAuthenticated(): boolean {
    return this.authenticationSubject.getValue();
  }

  /*
  signin(): Observable<boolean> {
    const body = new HttpParams().set('username', username).set('password', password);
    return this.httpClient.post<AuthenticationFlow>('signin', body, {withCredentials: true})
      .pipe(
        tap(flow => this.authenticationSubject.next(flow)),
        catchError(_ => of('NOT_AUTHENTICATED' as AuthenticationFlow))
      );
  }
   */

  signout(): Observable<void> {
    return this.httpClient.get<void>('logout', {withCredentials: true})
      .pipe(
        tap(() => this.authenticationSubject.next(false))
      );
  }

}
