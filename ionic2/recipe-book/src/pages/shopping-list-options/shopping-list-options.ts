import {Component} from '@angular/core';
import {ViewController} from "ionic-angular";

@Component({
  selector: 'page-shopping-list-options',
  template: `
    <ion-grid text-center>
      <ion-row>
        <ion-col>
          <h3>Store & Load</h3>
        </ion-col>
      </ion-row>
      <ion-row>
        <ion-col>
          <button ion-button outline (click)="onAction('load')">Load List</button>
        </ion-col>
      </ion-row>
      <ion-row>
        <ion-col>
          <button ion-button outline (click)="onAction('save')">Save List</button>
        </ion-col>
      </ion-row>  
    </ion-grid>
  `
})
export class ShoppingListOptionsPage {

  constructor(public viewCtrl: ViewController) {
  }

  onAction(action: string) {
    this.viewCtrl.dismiss({action});
  }

}
