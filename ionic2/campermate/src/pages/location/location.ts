import { NavController, Platform, AlertController } from 'ionic-angular';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { Geolocation } from 'ionic-native';
import { GoogleMaps } from '../../providers/google-maps';
import { Data } from '../../providers/data';

@Component({
  selector: 'page-location',
  templateUrl: 'location.html'
})
export class LocationPage {

  @ViewChild('map') mapElement: ElementRef;
  @ViewChild('pleaseConnect') pleaseConnect: ElementRef;

  latitude: number;
  longitude: number;

  constructor(public navCtrl: NavController, public maps: GoogleMaps, public platform: Platform, public dataService: Data, public alertCtrl: AlertController) {

  }

  ionViewDidLoad(): void {

    this.platform.ready().then(() => {

      this.dataService.getLocation().then((location) => {

        let savedLocation: any = false;

        if(location && typeof(location) != "undefined"){
          savedLocation = JSON.parse(location);
        }

        this.maps.init(this.mapElement.nativeElement, this.pleaseConnect.nativeElement).then(() => {

          if(savedLocation){

            this.latitude = savedLocation.latitude;
            this.longitude = savedLocation.longitude;

            this.maps.changeMarker(this.latitude, this.longitude);

          }

        });

      });

    });

  }

  setLocation(): void {

    Geolocation.getCurrentPosition().then((position) => {

      this.latitude = position.coords.latitude;
      this.longitude = position.coords.longitude;

      this.maps.changeMarker(position.coords.latitude, position.coords.longitude);

      let data = {
        latitude: this.latitude,
        longitude: this.longitude
      };

      this.dataService.setLocation(data);

      let alert = this.alertCtrl.create({
        title: 'Location set!',
        subTitle: 'You can now find your way back to your camp site from anywhere by clicking the button in the top right corner.',
        buttons: [{text: 'Ok'}]
      });

      alert.present();

    });

  }

  takeMeHome(): void {

    if (!this.latitude || !this.longitude) {

      let alert = this.alertCtrl.create({
        title: 'Nowhere to go!',
        subTitle: 'You need to set your camp location first. For now, want to launch Maps to find your own way home?',
        buttons: ['Ok']
      });

      alert.present();
    }
    else {

      let destination = this.latitude + ',' + this.longitude;

      if (this.platform.is('ios')) {
        window.open('maps://?q=' + destination, '_system');
      } else {
        let label = encodeURI('My Campsite');
        window.open('geo:0,0?q=' + destination + '(' + label + ')', '_system');
      }

    }
  }

}
