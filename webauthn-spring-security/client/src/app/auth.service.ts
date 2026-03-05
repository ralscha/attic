import {inject, Injectable} from '@angular/core';
import {from, Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from '../environments/environment';
import {catchError, map, tap} from 'rxjs/operators';
import {WebAuthnService} from './webauthn.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly httpClient = inject(HttpClient);
  private readonly webAuthnService = inject(WebAuthnService);

  private loggedIn = false;

  isAuthenticated(): Observable<boolean> {
    return this.httpClient.get<void>(`${environment.baseUrl}/authenticate`, {
      withCredentials: true
    }).pipe(
      tap(() => this.loggedIn = true),
      map(() => true),
      catchError(() => {
        this.loggedIn = false;
        return of(false);
      })
    );
  }

  logout(): Observable<void> {
    return this.httpClient.get<void>(`${environment.baseUrl}/logout`, {
      withCredentials: true
    }).pipe(tap(() => this.loggedIn = false));
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  authenticateWithWebAuthn(): Observable<string> {
    return from(this.webAuthnService.authenticate({}));
  }

  registerWebAuthn(label: string): Observable<void> {
    return from(this.webAuthnService.register({}, label));
  }

}



