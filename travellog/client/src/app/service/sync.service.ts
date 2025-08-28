import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppDatabase} from '../model/app-database';
import {BehaviorSubject, Observable} from 'rxjs';
import {SyncRequest} from '../model/sync-request';
import {SyncResponse} from '../model/sync-response';
import {SyncEntry} from '../model/sync-entry';

@Injectable({
  providedIn: 'root'
})
export abstract class SyncService<T extends SyncEntry> {

  protected readonly subject = new BehaviorSubject<T[]>([]);
  private readonly observable = this.subject.asObservable();

  protected constructor(private readonly httpClient: HttpClient,
                        protected readonly appDatabase: AppDatabase) {
  }

  getObservable(): Observable<T[]> {
    this.updateSubject();
    return this.observable;
  }

  getEntry(id: number): Promise<T> {
    // @ts-ignore
    return this.appDatabase[this.getTableName()].get(id);
  }

  async delete(entry: T): Promise<void> {
    entry.ts = -1;
    // @ts-ignore
    await this.appDatabase[this.getTableName()].put(entry);
    this.requestSync().catch(e => console.log(e));
  }

  async save(entry: T): Promise<void> {
    if (!entry.id) {
      entry.id = await this.getNextNewId();
      entry.ts = Math.floor(Date.now() / 1000);
      // @ts-ignore
      await this.appDatabase[this.getTableName()].add(entry);
      this.requestSync().catch(e => console.log(e));
    } else {
      // @ts-ignore
      const oldEntry = await this.appDatabase[this.getTableName()].get(entry.id);
      if (this.changed(oldEntry, entry)) {
        entry.ts = Math.floor(Date.now() / 1000);
        // @ts-ignore
        await this.appDatabase[this.getTableName()].put(entry);
        this.requestSync().catch(e => console.log(e));
      }
    }
  }

  async requestSync(): Promise<void> {
    this.updateSubject();

    const syncViewObject = await this.httpClient.get<{
      [key: string]: number
    }>(`/be/${this.getUrlPrefix()}_syncview`).toPromise();

    const syncView = new Map<number, number>();
    Object.entries(syncViewObject).forEach(kv => syncView.set(parseInt(kv[0], 10), kv[1]));

    const syncRequest: SyncRequest<T> = {
      inserted: [],
      updated: [],
      removed: [],
      gets: []
    };

    const deleteLocal: any[] = [];

    // @ts-ignore
    await this.appDatabase[this.getTableName()].toCollection().each(entry => {
      const serverTimestamp = syncView.get(entry.id);
      if (serverTimestamp) {
        if (entry.ts === -1) {
          syncRequest.removed.push(entry.id);
        } else if (entry.ts > serverTimestamp) {
          syncRequest.updated.push(entry);
        } else if (entry.ts < serverTimestamp) {
          syncRequest.gets.push(entry.id);
        }
        syncView.delete(entry.id);
      } else {
        // not on the server, either insert or delete locally
        if (entry.id < 0) {
          syncRequest.inserted.push(entry);
        } else {
          deleteLocal.push(entry.id);
        }
      }
    });

    // all these ids are not in our local database, fetch them
    syncView.forEach((value, key) => syncRequest.gets.push(key));

    // delete local entry
    let deleted = false;
    for (const id of deleteLocal) {
      // @ts-ignore
      await this.appDatabase[this.getTableName()].delete(id);
      deleted = true;
    }

    // if no changes end sync
    if (syncRequest.inserted.length === 0
      && syncRequest.updated.length === 0
      && syncRequest.removed.length === 0
      && syncRequest.gets.length === 0) {
      if (deleted) {
        this.updateSubject();
      }
      return Promise.resolve();
    }

    // send sync request to the server
    const syncResponse = await this.httpClient.post<SyncResponse<T>>(`/be/${this.getUrlPrefix()}_sync`,
      syncRequest).toPromise();

    // @ts-ignore
    await this.appDatabase.transaction('rw', this.appDatabase[this.getTableName()], async () => {
      if (syncResponse.gets && syncResponse.gets.length > 0) {
        // @ts-ignore
        await this.appDatabase[this.getTableName()].bulkPut(syncResponse.gets);
      }
      if (syncResponse.inserted) {
        for (const kv of Object.entries(syncResponse.inserted)) {
          const oldId = parseInt(kv[0], 10);
          // @ts-ignore
          const travelFromDb = await this.appDatabase[this.getTableName()].get(oldId);
          travelFromDb.id = kv[1].id;
          travelFromDb.ts = kv[1].ts;
          // @ts-ignore
          await this.appDatabase[this.getTableName()].delete(oldId);
          // @ts-ignore
          await this.appDatabase[this.getTableName()].add(travelFromDb);
        }
      }
      if (syncResponse.updated) {
        // @ts-ignore
        for (const kv of Object.entries(syncResponse.updated)) {
          // @ts-ignore
          await this.appDatabase[this.getTableName()].update(parseInt(kv[0], 10), {ts: kv[1]});
        }
      }
      if (syncResponse.removed) {
        for (const id of syncResponse.removed) {
          // @ts-ignore
          await this.appDatabase[this.getTableName()].delete(id);
        }
      }
    });

    this.updateSubject();
    return Promise.resolve();
  }

  protected abstract changed(oldEntry: T, newEntry: T): boolean;

  protected abstract getTableName(): string;

  protected abstract getUrlPrefix(): string;

  protected updateSubject(): void {
    // @ts-ignore
    this.appDatabase[this.getTableName()].where('ts').notEqual(-1).toArray().then(travel => {
      this.subject.next(travel);
    });
  }

  private async getNextNewId(): Promise<number> {
    // @ts-ignore
    const first = await this.appDatabase[this.getTableName()].toCollection().first();
    if (first) {
      if (first.id > 0) {
        return -1;
      } else {
        return first.id - 1;
      }
    }
    return -1;
  }

}
