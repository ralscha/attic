import {Component} from '@angular/core';
import {IonIcon, IonLabel, IonTabBar, IonTabButton, IonTabs} from "@ionic/angular/standalone";
import {addIcons} from "ionicons";
import {bodyOutline, eyeOutline, imageOutline, micOutline} from "ionicons/icons";

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.page.html',
  imports: [
    IonTabs,
    IonTabBar,
    IonTabButton,
    IonIcon,
    IonLabel
  ]
})
export class TabsPage {
  constructor() {
    addIcons({eyeOutline, imageOutline, bodyOutline, micOutline});
  }
}
