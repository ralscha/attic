import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

/*
  Generated class for the AlphaMale page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-alpha-male',
  templateUrl: 'alpha-male.html'
})
export class AlphaMalePage {

  constructor(public navCtrl: NavController) {}

  ionViewDidLoad() {
    console.log('Hello AlphaMale Page');
  }

}
