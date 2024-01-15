import { Storage } from '@ionic/storage';
import { Injectable } from '@angular/core';

@Injectable()
export class Data {

  constructor(public storage: Storage){

  }

  getData(): Promise<any> {
    return this.storage.get('photos');  
  }

  save(data): void {
    let newData = JSON.stringify(data);
    this.storage.set('photos', newData); 
  }

}