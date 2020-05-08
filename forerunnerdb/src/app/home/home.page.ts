import {Component, OnInit} from '@angular/core';
import {Earthquake} from '../earthquake';
import {Filter} from '../filter';
import {LoadingController, ModalController} from '@ionic/angular';
import {EarthquakeService} from '../earthquake.service';
import {FilterPage} from '../filter/filter.page';


@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {

  earthquakes: Earthquake[] = [];
  elapsedTime: number;

  geoWatchId: number;

  filter: Filter = {
    mag: {
      lower: -1,
      upper: 10
    },
    depth: {
      lower: -10,
      upper: 800
    },
    distance: {
      lower: 0,
      upper: 20000
    },
    time: '-1',
    sort: 'time',
    myLocation: null
  };

  constructor(private readonly earthquakeService: EarthquakeService,
              private readonly modalCtrl: ModalController,
              private readonly loadingCtrl: LoadingController) {
  }

  ngOnInit() {
    const storedFilter = localStorage.getItem('filter');
    if (storedFilter) {
      this.filter = JSON.parse(storedFilter);
    }
    navigator.geolocation.getCurrentPosition(position => {
      this.filter.myLocation = position.coords;

      this.earthquakeService.init()
        .then(() => this.filterEarthquakes())
        .catch(err => console.log(err));

    }, error => {
      this.filter.myLocation = {longitude: 7.5663964, latitude: 46.9268287};
      this.earthquakeService.init()
        .then(() => this.filterEarthquakes())
        .catch(err => console.log(err));
      console.log('Error getting location', error);
    });

    if (this.geoWatchId) {
      navigator.geolocation.clearWatch(this.geoWatchId);
    }

    this.geoWatchId = navigator.geolocation.watchPosition(position => {
      this.filter.myLocation = position.coords;
    });
  }

  doRefresh(event) {
    this.earthquakeService.init()
      .then(() => this.filterEarthquakes(true))
      .then(() => event.target.complete());
  }

  async filterEarthquakes(hideLoading = false) {
    localStorage.setItem('filter', JSON.stringify(this.filter));

    let loading = null;

    if (!hideLoading) {
      loading = await this.loadingCtrl.create({
        message: 'Please wait...'
      });
      await loading.present();
    }

    const start = performance.now();
    this.earthquakes = await this.earthquakeService.filter(this.filter);
    this.elapsedTime = performance.now() - start;

    if (loading) {
      loading.dismiss();
    }
  }

  async presentFilterPage() {
    const filterPage = await this.modalCtrl.create(
      {
        component: FilterPage,
        componentProps: {filter: this.filter}
      }
    );

    await filterPage.present();
    const event = await filterPage.onDidDismiss();

    if (event && event.data) {
      this.filter = event.data;
      await this.filterEarthquakes();
    }

  }

}
