import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';

@Injectable()
export class Data {

  constructor(private readonly storage: Storage) {
  }

  getData(): Promise<any> {
    return this.storage.get('settings');
  }

  save(data): void {
    let newData = JSON.stringify(data);
    this.storage.set('settings', newData);
  }

}
