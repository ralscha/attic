import {Component} from '@angular/core';
import {Platform, App} from 'ionic-angular';
import {StatusBar, Splashscreen} from 'ionic-native';

import {HomePage} from '../pages/home/home';


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage = HomePage;

  constructor(private platform: Platform, private app: App) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      StatusBar.styleDefault();
      Splashscreen.hide();

      //Registration of push in Android and Windows Phone
      console.log('register');
      platform.registerBackButtonAction(() => {
        let nav = this.app.getActiveNav();
        console.log(nav);
        console.log(nav.canGoBack());
        if (nav.canGoBack()) { //Can we go back?
          nav.pop();
        } else {
          this.platform.exitApp(); //Exit from app
        }
      });

      document.addEventListener('backbutton', function (event) {
        event.preventDefault();
        event.stopPropagation();
        console.log('hey yaaahh');
      }, false);

    });
  }
}
