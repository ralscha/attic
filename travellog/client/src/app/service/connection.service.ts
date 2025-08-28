import {Injectable} from '@angular/core';
import {defer, from, fromEvent, iif, interval, merge, Observable, of, Subject} from 'rxjs';
import {catchError, distinctUntilChanged, map, mapTo, shareReplay, switchMap, takeWhile} from 'rxjs/operators';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AppDatabase} from '../model/app-database';

enum Connection {
  OFFLINE = 'OFFLINE', ONLINE = 'ONLINE'
}

enum Authentication {
  USER = 'USER', ADMIN = 'ADMIN'
}

export class ConnectionState {
  constructor(private readonly connection: Connection, private readonly authentication: Authentication) {
  }

  isOnlineAuthenticated(): boolean {
    return this.connection === Connection.ONLINE && this.authentication !== null;
  }

  isOnline() {
    return this.connection === Connection.ONLINE;
  }

  isOffline() {
    return this.connection === Connection.OFFLINE;
  }

  isAdmin() {
    return this.authentication === Authentication.ADMIN;
  }

  isUser() {
    return this.authentication === Authentication.USER;
  }

  isAuthenticated() {
    return this.authentication !== null;
  }

  isEqualsTo(other: ConnectionState) {
    return this.connection === other.connection && this.authentication === other.authentication;
  }
}

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {

  private readonly connectionState$: Observable<ConnectionState>;
  private readonly lastConnectionState$: Observable<ConnectionState>;
  private readonly onlineCheck$: Observable<ConnectionState>;
  private readonly authenticationToken$: Observable<ConnectionState>;
  private readonly manualInject: Subject<ConnectionState> = new Subject();
  private readonly reCheckSubject: Subject<Connection> = new Subject();

  constructor(private readonly httpClient: HttpClient,
              private readonly appDatabase: AppDatabase) {

    this.authenticationToken$ = defer(() => from(this.appDatabase.authenticationToken.limit(1).first())
      .pipe(
        map(token => token ? new ConnectionState(Connection.OFFLINE, Authentication.USER) :
          new ConnectionState(Connection.OFFLINE, null))
      ));

    this.onlineCheck$ = this.httpClient.get('/be/authenticate', {responseType: 'text'})
      .pipe(
        map(response => this.handleAuthSuccess(response)),
        catchError(error => this.handleAuthError(error))
      );

    this.connectionState$ =
      merge(
        defer(() => of(window.navigator.onLine ? Connection.ONLINE : Connection.OFFLINE)),
        this.reCheckSubject,
        fromEvent(window, 'offline').pipe(mapTo(Connection.OFFLINE)),
        fromEvent(window, 'online').pipe(mapTo(Connection.ONLINE))
      )
        .pipe(
          switchMap(conn => iif(() => conn === Connection.OFFLINE, this.authenticationToken$, this.onlineCheck$)),
          switchMap(s => {
              if (window.navigator.onLine && s.isOffline() && s.isAuthenticated()) {
                return merge(
                  of(s),
                  interval(60_000).pipe(
                    switchMap(() => this.onlineCheck$),
                    takeWhile(cs => window.navigator.onLine && cs.isOffline(), true)
                  ));
              }

              return of(s);
            }
          ),
          distinctUntilChanged((a, b) => a.isEqualsTo(b))
        );

    this.lastConnectionState$ = merge(this.manualInject, this.connectionState$).pipe(shareReplay(1));
  }

  connectionState(): Observable<ConnectionState> {
    return this.lastConnectionState$;
  }

  manualNext(online: boolean, authority: 'ADMIN' | 'USER' | null = null): ConnectionState {
    let auth: Authentication = null;
    if (authority === 'ADMIN') {
      auth = Authentication.ADMIN;
    } else if (authority === 'USER') {
      auth = Authentication.USER;
    }

    let connection = Connection.ONLINE;
    if (!online) {
      connection = Connection.OFFLINE;
    }

    const cs = new ConnectionState(connection, auth);
    this.manualInject.next(cs);

    return cs;
  }

  reconnect() {
    if (window.navigator.onLine) {
      this.onlineCheck$.subscribe(cs => {
        if (cs.isOnline()) {
          this.manualInject.next(cs);
        }
      });
    }
  }

  logout(online: boolean) {
    if (online) {
      this.manualNext(online);
    } else {
      this.reCheckSubject.next(Connection.OFFLINE);
    }
  }

  private handleAuthSuccess(response: string): ConnectionState {
    if (response === 'ADMIN') {
      return new ConnectionState(Connection.ONLINE, Authentication.ADMIN);
    } else if (response === 'USER') {
      return new ConnectionState(Connection.ONLINE, Authentication.USER);
    }
    this.appDatabase.authenticationToken.clear();
    return null;
  }

  private handleAuthError(error: HttpErrorResponse): Observable<ConnectionState> {
    if (error.status === 401) {
      // token already deleted on the server
      this.appDatabase.authenticationToken.clear();
      return of(new ConnectionState(Connection.ONLINE, null));
    }

    return this.authenticationToken$;
  }
}
