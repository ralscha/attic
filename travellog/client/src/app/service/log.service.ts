import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppDatabase} from '../model/app-database';
import {Log} from '../model/log';
import {SyncService} from './sync.service';

@Injectable({
  providedIn: 'root'
})
export class LogService extends SyncService<Log> {

  private travelId: number;

  constructor(httpClient: HttpClient, appDatabase: AppDatabase) {
    super(httpClient, appDatabase);
  }

  setTravelId(travelId: number): void {
    this.travelId = travelId;
  }

  getTravelId(): number {
    return this.travelId;
  }

  protected changed(oldEntry: Log, newEntry: Log): boolean {
    return oldEntry.location !== newEntry.location
      || oldEntry.report !== newEntry.report
      || oldEntry.created !== newEntry.created
      || oldEntry.lat !== newEntry.lat
      || oldEntry.lng !== newEntry.lng;
  }

  protected override updateSubject(): void {
    // @ts-ignore
    this.appDatabase[this.getTableName()].where('ts').notEqual(-1).and(log => log.travelId === this.travelId).toArray().then(log => {
      this.subject.next(log);
    });
  }

  protected getTableName(): string {
    return 'log';
  }

  protected getUrlPrefix(): string {
    return 'log';
  }

}
