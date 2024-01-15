import {Component} from '@angular/core';
import {
  AlertController,
  LoadingController,
  NavController,
  Platform
} from 'ionic-angular';
import {InAppPurchase} from 'ionic-native';
import {AlphaMalePage} from '../../pages/alpha-male/alpha-male';
import {ClimateHeroPage} from '../../pages/climate-hero/climate-hero';
import {GrandTheftNoughtoPage} from '../../pages/grand-theft-noughto/grand-theft-noughto';
import {HappyFamiliesPage} from '../../pages/happy-families/happy-families';
import {KingForADayPage} from '../../pages/king-for-a-day/king-for-a-day';
import {WorldTravellerPage} from '../../pages/world-traveller/world-traveller';
import {Database} from '../../providers/database';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  public happyFamilies: boolean = false;
  public climateHero: boolean = false;
  public alphaMale: boolean = false;
  public grandTheftNoughto: boolean = false;
  public worldTraveller: boolean = false;
  public kingForADay: boolean = false;
  public goTo;
  private loader;
  public products = [
    'com.saintsatplay.happyfamilies',
    'com.saintsatplay.climatehero',
    'com.saintsatplay.alphamale',
    'com.saintsatplay.grandtheftnoughto',
    'com.saintsatplay.worldtraveller',
    'com.saintsatplay.kingforaday'
  ];

  constructor(public alertCtrl: AlertController,
              public DB: Database,
              public loadingCtrl: LoadingController,
              public navCtrl: NavController,
              public platform: Platform) {
    this.platform.ready().then(() => {
      DB.createDatabase();
      this.initiateProductCheckAndParsing();
    });
  }

  initiateProductCheckAndParsing() {
    this.platform.ready().then(() => {
      this.displayPreloader();

      InAppPurchase
        .getProducts(this.products)
        .then((products) => {
          this.DB.retrievePurchases()
            .then((data) => {
              this.hidePreloader();
              this.determineProductsToEnable(data);
            })
            .catch(function (err) {
              this.hidePreloader();
              this.displayAlert('Retrieving Saved purchases', err);
            });
        })
        .catch((err) => {
          this.hidePreloader();
          this.displayNetworkErrorMessage('Network error', err.errorMessage + '.<br><br>Please double check your network connection and try again');
        });

    });
  }

  determineProductsToEnable(data) {
    this.platform.ready().then(() => {
      let k;
      for (k in data) {
        this.DB.doesPurchaseExistInTable(data[k].productId);
        if (data[k].productId === "com.saintsatplay.happyfamilies") {
          this.happyFamilies = true;
        }

        if (data[k].productId === "com.saintsatplay.climatehero") {
          this.climateHero = true;
        }

        if (data[k].productId === "com.saintsatplay.alphamale") {
          this.alphaMale = true;
        }

        if (data[k].productId === "com.saintsatplay.grandtheftnoughto") {
          this.grandTheftNoughto = true;
        }

        if (data[k].productId === "com.saintsatplay.worldtraveller") {
          this.worldTraveller = true;
        }

        if (data[k].productId === "com.saintsatplay.kingforaday") {
          this.kingForADay = true;
        }
      }
    });
  };

  displayPreloader() {
    this.loader = this.loadingCtrl.create({
      content: "Please wait.."
    });
    this.loader.present();
  };

  hidePreloader() {
    this.loader.dismissAll();
  }

  playGame(page) {
    switch (page) {
      case "AlphaMale":
        this.goTo = AlphaMalePage;
        break;

      case "ClimateHero":
        this.goTo = ClimateHeroPage;
        break;

      case "HappyFamilies":
        this.goTo = HappyFamiliesPage;
        break;

      case "GrandTheftNoughto":
        this.goTo = GrandTheftNoughtoPage;
        break;
      case "WorldTraveller":
        this.goTo = WorldTravellerPage;
        break;

      case "KingForADay":
        this.goTo = KingForADayPage;
        break;

    }
    this.navCtrl.push(this.goTo);
  }

  purchaseProduct(product) {
    this.platform.ready().then(() => {
      var productID = product,
        purchasedItem = [{"productId": productID}];

      this.displayPreloader();

      InAppPurchase
        .buy(productID)
        .then((data) => {
          this.hidePreloader();
          this.determineProductsToEnable(purchasedItem);
        })
        .catch((err) => {
          this.hidePreloader();
        });
    });
  }

  restoreProductListings() {
    this.platform.ready().then(() => {
      this.displayPreloader();
      InAppPurchase
        .restorePurchases()
        .then((data) => {
          this.hidePreloader();
          this.determineProductsToEnable(data);
        })
        .catch((err) => {
          console.log(err);
        });
    });
  }

  displayAlert(title, message): void {
    let headsUp = this.alertCtrl.create({
      title: title,
      subTitle: message,
      buttons: ['Got It!']
    });
    headsUp.present();
  }

  displayNetworkErrorMessage(title, message): void {
    let headsUp = this.alertCtrl.create({
      title: title,
      subTitle: message,
      buttons: [
        {
          text: 'Got It!',
          handler: () => {
            this.initiateProductCheckAndParsing();
          }
        }]
    });
    headsUp.present();
  }

}
