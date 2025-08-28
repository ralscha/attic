export interface SyncResponse<T> {
  gets: T[];
  updated: { [key: string]: number };
  inserted: { [key: string]: { id: number, ts: number } };
  removed: number[];
}
