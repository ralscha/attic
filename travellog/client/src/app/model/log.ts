import {SyncEntry} from './sync-entry';

export interface Log extends SyncEntry {
  travelId: number;
  created: number;
  lat: number;
  lng: number;
  location: number;
  report: number;
}
