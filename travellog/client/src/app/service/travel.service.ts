import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppDatabase} from '../model/app-database';
import {Travel} from '../model/travel';
import {SyncService} from './sync.service';

@Injectable({
  providedIn: 'root'
})
export class TravelService extends SyncService<Travel> {

  constructor(httpClient: HttpClient, appDatabase: AppDatabase) {
    super(httpClient, appDatabase);
  }

  async getDefaultTravelId(): Promise<number | undefined> {
    const defaultTravelId = await this.appDatabase.defaultTravel.limit(1).first();
    if (!defaultTravelId) {
      const firstTravel = await this.appDatabase.travel.limit(1).first();
      if (firstTravel) {
        return firstTravel.id;
      }
    }
    return defaultTravelId;
  }

  async getDefaultTravelName(): Promise<string | null | undefined> {
    const travelId = await this.getDefaultTravelId();
    if (travelId) {
      return (await this.appDatabase.travel.get(travelId))?.name;
    }
    return null;
  }

  protected changed(oldEntry: Travel, newEntry: Travel): boolean {
    return oldEntry?.name !== newEntry?.name;
  }

  protected getTableName(): string {
    return 'travel';
  }

  protected getUrlPrefix(): string {
    return 'travel';
  }

}
