import {ErrorHandler, Injectable} from '@angular/core';
import {AppDatabase} from './model/app-database';
import {HttpClient} from '@angular/common/http';
import {ConnectionService} from './service/connection.service';
import {catchError, filter, mapTo, mergeMap, switchMap, take} from 'rxjs/operators';
import {environment} from '../environments/environment';
import {from, iif, noop} from 'rxjs';

@Injectable()
export class AppGlobalErrorhandler implements ErrorHandler {

  constructor(private readonly appDatabase: AppDatabase,
              private readonly httpClient: HttpClient,
              private readonly connectionService: ConnectionService) {

    this.connectionService.connectionState()
      .pipe(
        filter(cs => cs.isOnline()),
        switchMap(() => from(this.appDatabase.errors.toArray())),
        filter(errors => errors.length > 0),
        switchMap(errors => this.httpClient.post<void>('/be/client-error', errors).pipe(mapTo(errors))),
        switchMap(errors => this.appDatabase.errors.bulkDelete(errors.map(error => "" + error.id)))
      )
      .subscribe(noop, noop);
  }

  async handleError(error: any): Promise<void> {
    if (!environment.production) {
      console.error(error);
    }

    // @ts-ignore
    const connection = navigator.connection;

    const userAgent = {
      language: navigator.language,
      platform: navigator.platform,
      userAgent: navigator.userAgent,
      connectionType: connection?.type,
    };

    const errorMsg = error && error.message ? error.message : error;
    const body = JSON.stringify({ts: Date.now(), userAgent, error: errorMsg});

    this.connectionService.connectionState().pipe(
      take(1),
      mergeMap(cs => iif(() => cs.isOnline(),
        this.httpClient.post<void>('/be/client-error', [body]),
        this.appDatabase.errors.add({error: body}))
      ),
      catchError(() => this.appDatabase.errors.add({error: body}))
    ).subscribe(noop);
  }

}
