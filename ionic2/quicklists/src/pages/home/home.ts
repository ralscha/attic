import {Component} from '@angular/core';
import {NavController, AlertController, Platform, Alert} from 'ionic-angular';
import {Data} from "../../providers/data";
import {ChecklistModel} from "../../models/checklist-model";
import {ChecklistPage} from "../checklist/checklist";
import {Keyboard} from "ionic-native";
import {Storage} from "@ionic/storage";
import {IntroPage} from "../intro/intro";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  checklists: ChecklistModel[] = [];

  constructor(public navCtrl: NavController, public dataService: Data, public alertCtrl: AlertController,
              public platform: Platform, public storage: Storage) {
  }

  ionViewDidLoad() {
    this.platform.ready().then(() => {
      this.storage.get('introShown').then((result) => {
        if (!result) {
          this.storage.set('introShown', true);
          this.navCtrl.setRoot(IntroPage);
        }
      });

      this.dataService.getData().then((checklists) => {
        let savedChecklists: any = false;
        if (typeof(checklists) != "undefined") {
          savedChecklists = JSON.parse(checklists);
        }
        if (savedChecklists) {
          savedChecklists.forEach((savedChecklist) => {
            let loadChecklist = new ChecklistModel(savedChecklist.title, savedChecklist.items);
            this.checklists.push(loadChecklist);

            loadChecklist.checklist.subscribe(update => {
              this.save();
            });
          });
        }
      });
    });
  }

  addChecklist(): void {
    const prompt: Alert = this.alertCtrl.create({
      title: 'New Checklist',
      message: 'Enter the name of your new checklist below:',
      inputs: [{
        name: 'name'
      }],
      buttons: [{
        text: 'Cancel'
      }, {
        text: 'Save',
        handler: data => {
          const newChecklist = new ChecklistModel(data.name);
          this.checklists.push(newChecklist);
          newChecklist.checklist.subscribe(update => this.save());
          this.save();
        }
      }]
    });

    prompt.present();
  }

  renameChecklist(checklist): void {
    const prompt: Alert = this.alertCtrl.create({
      title: 'Rename Checklist',
      message: 'Enter the new name of this checklist below:',
      inputs: [{
        name: 'name'
      }],
      buttons: [{
        text: 'Cancel'
      }, {
        text: 'Save',
        handler: data => {
          const index = this.checklists.indexOf(checklist);
          if (index > -1) {
            this.checklists[index].setTitle(data.name);
            this.save();
          }
        }
      }]
    });

    prompt.present();
  }

  viewChecklist(checklist): void {
    this.navCtrl.push(ChecklistPage, {
      checklist: checklist
    });
  }

  removeChecklist(checklist): void {
    const index = this.checklists.indexOf(checklist);
    if (index > -1) {
      this.checklists.splice(index, 1);
      this.save();
    }
  }

  save(): void {
    Keyboard.close();
    this.dataService.save(this.checklists);
  }

}
