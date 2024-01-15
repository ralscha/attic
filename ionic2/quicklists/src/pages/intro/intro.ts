import {Component} from '@angular/core';
import {NavController} from 'ionic-angular';
import {HomePage} from '../home/home';

@Component({
  selector: 'page-intro',
  templateUrl: 'intro.html'
})
export class IntroPage {

  constructor(public nav: NavController) {
  }

  goToHome(): void {
    this.nav.setRoot(HomePage);
  }
}
