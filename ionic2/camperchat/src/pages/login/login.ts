import { Component } from '@angular/core';
import { Platform, NavController, MenuController, AlertController, LoadingController } from 'ionic-angular';
import { Facebook } from 'ionic-native';
import { HomePage } from '../home/home';
import { Data } from '../../providers/data';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html'
})
export class LoginPage {

  loading: any;

  constructor(public nav: NavController, public platform: Platform, public menu: MenuController, public dataService: Data, public alertCtrl: AlertController, public loadingCtrl: LoadingController) {

    this.loading = this.loadingCtrl.create({
      content: 'Authenticating...'
    });

    this.menu.enable(false);
  }

  login(): void {

    this.loading.present();

    Facebook.login(['public_profile']).then((response) => {

      this.getProfile();

    }, (err) => {

      let alert = this.alertCtrl.create({
        title: 'Oops!',
        subTitle: 'Something went wrong, please try again later.',
        buttons: ['Ok']
      });

      this.loading.dismiss();
      alert.present();

    });

  }

  getProfile(): void {

    this.menu.enable(true);
    this.loading.dismiss();
    this.nav.setRoot(HomePage);
    /*
    Facebook.api('/me?fields=id,name,picture', ['public_profile']).then(

      (response) => {

        console.log(response);

        this.dataService.fbid = response.id;
        this.dataService.username = response.name;
        this.dataService.picture = response.picture.data.url;

        this.menu.enable(true);
        this.loading.dismiss();
        this.nav.setRoot(HomePage);

      },

      (err) => {

        console.log(err);

        let alert = this.alertCtrl.create({
          title: 'Oops!',
          subTitle: 'Something went wrong, please try again later.',
          buttons: ['Ok']
        });

        this.loading.dismiss();
        alert.present();

      }

    );
    */

  }

}
