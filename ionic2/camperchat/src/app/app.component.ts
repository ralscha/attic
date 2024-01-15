import { Component, ViewChild } from "@angular/core";
import { Facebook } from 'ionic-native';
import { Nav, Platform, MenuController } from 'ionic-angular';
import { StatusBar } from 'ionic-native';
import { HomePage } from '../pages/home/home';
import { AboutPage } from '../pages/about/about';
import { LoginPage } from '../pages/login/login';
import { Data } from '../providers/data';

@Component({
  templateUrl: 'app.html'
})
export class MyApp {

  @ViewChild(Nav) nav: Nav;

  rootPage: any = LoginPage;
  homePage: any = HomePage;
  aboutPage: any = AboutPage;

  constructor(public platform: Platform, public dataService: Data, public menu: MenuController) {

    platform.ready().then(() => {

    });

  }

  openPage(page): void {
    this.menu.close();
    this.nav.setRoot(page);
  }

  logout(): void {

    this.menu.close();
    this.menu.enable(false);

    this.nav.setRoot(LoginPage);

    this.dataService.fbid = null;
    this.dataService.username = null;
    this.dataService.picture = null;

    //Facebook.logout();
  }

}
