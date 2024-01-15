import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

/*
  Generated class for the KingForADay page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-king-for-a-day',
  templateUrl: 'king-for-a-day.html'
})
export class KingForADayPage {

  constructor(public navCtrl: NavController) {}

  ionViewDidLoad() {
    console.log('Hello KingForADayPage Page');
  }

}
