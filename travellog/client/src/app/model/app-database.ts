import Dexie from 'dexie';
import {Injectable} from '@angular/core';
import {ClientError} from './client-error';
import {Travel} from './travel';
import {Log} from './log';

@Injectable({
  providedIn: 'root'
})
export class AppDatabase extends Dexie {
  authenticationToken!: Dexie.Table<string, number>;
  defaultTravel!: Dexie.Table<number, number>;
  invalidAuthenticationTokens!: Dexie.Table<string, number>;
  travel!: Dexie.Table<Travel, number>;
  log!: Dexie.Table<Log, number>;
  errors!: Dexie.Table<ClientError, string>;

  constructor() {
    super('TravelLogDatabase');
    this.version(1).stores({
      authenticationToken: '++',
      defaultTravel: '++',
      invalidAuthenticationTokens: '++',
      travel: 'id,ts',
      log: 'id,ts,travelId',
      errors: '++id'
    });
  }
}
