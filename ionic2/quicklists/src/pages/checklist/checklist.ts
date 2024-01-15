import { Component } from '@angular/core';
import {NavController, NavParams, AlertController, Alert} from 'ionic-angular';

@Component({
  selector: 'page-checklist',
  templateUrl: 'checklist.html'
})
export class ChecklistPage {

  checklist: any;

  constructor(public navCtrl: NavController, public navParams: NavParams, public alertCtrl: AlertController) {
    this.checklist = this.navParams.get('checklist');
  }

  addItem(): void {
    const prompt: Alert = this.alertCtrl.create({
      title: 'Add Item',
      message: 'Enter the name of the task for this checklist below',
      inputs: [{
        name: 'name'
      }],
      buttons: [
        {text: 'Cancel'},
        {
          text: 'Save',
          handler: data => this.checklist.addItem(data.name)
        }
      ]
    });
    prompt.present();
  }

  toggleItem(item): void {
    this.checklist.toggleItem(item);
  }

  removeItem(item): void {
    this.checklist.removeItem(item);
  }

  renameItem(item): void {
    const prompt: Alert = this.alertCtrl.create({
      title: 'Rename Item',
      message: 'Enter the new name of this checklist below',
      inputs: [{
        name: 'name'
      }],
      buttons: [
        {text: 'Cancel'},
        {
          text: 'Save',
          handler: data => this.checklist.renameItem(item, data.name)
        }
      ]
    });
    prompt.present();
  }

  uncheckItems(): void {
    this.checklist.items.forEach(item => {
      if (item.checked) {
         this.checklist.toggleItem(item);
      }
    });
  }

}
