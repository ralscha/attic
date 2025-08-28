export interface SyncRequest<T> {
  inserted: T[];
  updated: T[];
  removed: number[];
  gets: number[];
}
