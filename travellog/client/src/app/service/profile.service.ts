import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Session} from '../model/session';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private readonly httpClient: HttpClient) {
  }

  changePassword(oldPassword: string, newPassword: string): Observable<'INVALID' | 'WEAK_PASSWORD' | null> {
    const body = new HttpParams().set('oldPassword', oldPassword).set('newPassword', newPassword);
    return this.httpClient.post<'INVALID' | 'WEAK_PASSWORD' | null>('/be/change-password', body);
  }

  deleteAccount(password: string): Observable<boolean> {
    return this.httpClient.post('/be/delete-account', password, {responseType: 'text'})
      .pipe(map(response => response === 'true'));
  }

  fetchSessions(): Observable<Session[]> {
    return this.httpClient.get<Session[]>('/be/sessions');
  }

  deleteSession(sessionId: string): Observable<void> {
    return this.httpClient.post<void>('/be/delete-session', sessionId);
  }

  changeEmail(newEmail: string, password: string): Observable<string> {
    const body = new HttpParams().set('newEmail', newEmail).set('password', password);
    return this.httpClient.post<string>('/be/change-email', body);
  }

  confirmEmailChange(token: string): Observable<boolean> {
    return this.httpClient.post('/be/confirm-email-change', token, {responseType: 'text'})
      .pipe(map(response => response === 'true'));
  }
}
