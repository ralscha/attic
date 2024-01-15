import {Component} from '@angular/core';
import {Toggle} from "ionic-angular";
import {SettingsService} from "../../providers/settings-service";

@Component({
  selector: 'page-settings',
  templateUrl: 'settings.html'
})
export class SettingsPage {

  constructor(private settingsService: SettingsService) {
  }

  onToggle(event: Toggle) {
    this.settingsService.toggleAlternativeBackground(event.checked);
  }

  isAlternativeBackground(): boolean {
    return this.settingsService.isAlternativeBackground();
  }
}
