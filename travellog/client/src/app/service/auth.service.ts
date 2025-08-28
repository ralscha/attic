import {Injectable} from '@angular/core';
import {from, Observable, of} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpParams, HttpResponse} from '@angular/common/http';
import {catchError, filter, map, switchMap, tap} from 'rxjs/operators';
import {AppDatabase} from '../model/app-database';
import {ConnectionService, ConnectionState} from './connection.service';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private readonly httpClient: HttpClient,
              private readonly appDatabase: AppDatabase,
              private readonly connectionService: ConnectionService) {

    this.connectionService.connectionState()
      .pipe(
        filter(cs => cs.isOnlineAuthenticated()),
        switchMap(() => from(this.appDatabase.invalidAuthenticationTokens.toArray())),
        filter(tokens => tokens.length > 0),
        switchMap(tokens => this.httpClient.post<void>('/be/invalidate-sessions', tokens))
      )
      .subscribe(() => this.appDatabase.invalidAuthenticationTokens.clear(), error => console.log(error));
  }

  login(username: string, password: string): Observable<ConnectionState> {
    const body = new HttpParams().set('username', username).set('password', password);

    return this.httpClient.post('/be/login', body, {
      responseType: 'text',
      observe: 'response'
    }).pipe(
      switchMap(response => this.handleLoginResponse(response)),
      catchError(error => of(this.handleLoginError(error)))
    );
  }

  logout(): Observable<void> {
    return this.httpClient.get<void>('/be/logout')
      .pipe(
        tap(async () => {
          await this.deleteTokens();
          this.connectionService.logout(true);
        }),
        catchError(error => from(this.handleLogoutError(error)))
      );
  }

  signup(email: string, password: string): Observable<'EMAIL_REGISTERED' | 'WEAK_PASSWORD' | null> {
    const body = new HttpParams().set('email', email).set('password', password);
    return this.httpClient.post<'EMAIL_REGISTERED' | 'WEAK_PASSWORD' | null>('/be/signup', body);
  }

  confirmSignup(token: string): Observable<boolean> {
    return this.httpClient.post('/be/confirm-signup', token, {responseType: 'text'})
      .pipe(
        map(response => response === 'true')
      );
  }

  resetPasswordRequest(email: string): Observable<boolean> {
    return this.httpClient.post('/be/reset-password-request', email, {responseType: 'text'})
      .pipe(
        map(response => response === 'true')
      );
  }

  resetPassword(resetToken: string, password: string): Observable<'INVALID' | 'WEAK_PASSWORD' | null> {
    const body = new HttpParams().set('resetToken', resetToken).set('password', password);
    return this.httpClient.post<'INVALID' | 'WEAK_PASSWORD' | null>('/be/reset-password', body);
  }

  async deleteTokens() {
    sessionStorage.removeItem('token');
    await this.appDatabase.authenticationToken.clear();
  }

  private async handleLoginResponse(response: HttpResponse<string>): Promise<ConnectionState> {
    const oldTokens = await this.appDatabase.authenticationToken.toArray();
    if (oldTokens.length > 0) {
      await this.appDatabase.invalidAuthenticationTokens.bulkPut(oldTokens);
      await this.deleteTokens();
    }

    const newToken = response.headers.get('x-authentication');
    if (newToken) {
      if (response.body === 'ADMIN') {
        sessionStorage.setItem('token', newToken);
        return this.connectionService.manualNext(true, 'ADMIN');
      } else if (response.body === 'USER') {
        await this.appDatabase.authenticationToken.put(newToken);
        return this.connectionService.manualNext(true, 'USER');
      } else {
        await this.deleteTokens();
      }
    }

    return this.connectionService.manualNext(true);
  }

  private handleLoginError(error: HttpErrorResponse): ConnectionState {
    if (error.status === 401) {
      return this.connectionService.manualNext(true);
    }
    return this.connectionService.manualNext(false);
  }

  private async handleLogoutError(error: HttpErrorResponse): Promise<void> {
    const tokens = await this.appDatabase.authenticationToken.toArray();
    if (tokens.length > 0) {
      await this.appDatabase.invalidAuthenticationTokens.bulkPut(tokens);
    }

    await this.deleteTokens();

    if (error.status === 401) {
      this.connectionService.logout(true);
    } else {
      this.connectionService.logout(false);
    }

    return Promise.resolve();
  }


}
