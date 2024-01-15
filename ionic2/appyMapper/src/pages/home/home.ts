import {Component, ViewChild} from '@angular/core';
import {Content, Platform, ToastController} from 'ionic-angular';
import {Geolocation, GoogleMap, GoogleMapsAnimation, GoogleMapsEvent, GoogleMapsLatLng} from 'ionic-native';
import {Database} from '../../providers/database';
import {Distances} from '../../providers/distances';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  @ViewChild(Content) content: Content;

  private coords: any = {};
  private europe: any = {};
  private map: GoogleMap;
  private location: any;
  public allStores: any;
  public locations: any;
  public locationsPresent: any = false;
  public countries: any;
  public markers: any = [];
  public byCountry: any;
  public byNearest: any;
  public displayFilters: boolean = false;
  public filtersText: string = "Display Filters";
  public summary: string;

  constructor(public platform: Platform,
              public DB: Database,
              public DIST: Distances,
              public toastCtrl: ToastController) {

    this.platform.ready().then(() => {

      GoogleMap.isAvailable()
        .then((isAvailable: boolean) => {
          if (!isAvailable) {
            console.log('GoogleMap plugin is NOT available');
          }
          else {
            console.log('GoogleMap plugin is available');
            Geolocation.getCurrentPosition().then((resp) => {
              this.coords = {
                lat: resp.coords.latitude,
                lng: resp.coords.longitude
              };

              this.europe = {
                lat: '50.537421',
                lng: '15.114438'
              };

              this.location = new GoogleMapsLatLng(this.europe.lat, this.europe.lng);

              this.map = new GoogleMap('map', {
                'backgroundColor': 'white',
                'controls': {
                  'compass': true,
                  'indoorPicker': true,
                  'zoom': true
                },
                'camera': {
                  'latLng': this.location,
                  'tilt': 90,
                  'zoom': 3,
                  'bearing': 0
                },
                'gestures': {
                  'scroll': true,
                  'tilt': true,
                  'rotate': true,
                  'zoom': true
                }
              });


              this.map.on(GoogleMapsEvent.MAP_READY).subscribe(() => {

                DB.retrieveStorelocations().then((data) => {
                  this.allStores = data;
                  this.locations = data;
                  this.renderLocations(this.map, this.locations, null, null);
                  this.summary = `${this.locations.length} Apple Stores`;
                });


                DB.retrieveCountries().then((data) => {
                  this.countries = data;
                });


              });

            })
              .catch((error) => {
                console.dir(error);
              });
          }
        });

    });

  }

  renderLocations(map, locations, lat = null, lng = null, zoom = null) {
    let k;

    if (lat !== null && lng !== null && zoom !== null) {
      let LOCATIONS = new GoogleMapsLatLng(lat, lng);
      map.animateCamera({
          'target': LOCATIONS,
          'tilt': 90,
          'zoom': zoom,
          'bearing': 0,
          'duration': 1500
        },
        function () {
          console.log("The animation is done");
        });
    }


    if (locations.length !== 0) {
      for (k in locations) {
        let markerOptions = {
          'position': new GoogleMapsLatLng(locations[k].lat, locations[k].lng),
          'title': locations[k].name,
          animation: GoogleMapsAnimation.DROP,
          'styles': {
            'text-align': 'right',
            'color': 'grey'
          }
        };

        map.addMarker(markerOptions, this.onMarkerAdded);

      };

      this.locationsPresent = true;
      this.locations = locations;
    }
    else {
      let message = "No stores were found for your selected search criteria. Please try a different search.";
      this.storeNotification(message);
      this.locationsPresent = false;
    }
  }


  renderAllStoreLocations() {
    this.scrollToTopOfScreen();
    this.removeMapMarkers();
    this.locations = this.allStores;

    let zoom = 3;

    this.renderLocations(this.map, this.locations, this.europe.lat, this.europe.lng, zoom);
    this.displayLocationFilters();
    this.summary = `${this.locations.length} Apple Stores`;
  }


  renderLocationsByCountry(id) {
    let j,
      k,
      stores = [],
      country,
      lat,
      lng,
      zoom,
      num = Number(id);


    for (k in this.countries) {
      if (num === this.countries[k].id) {
        country = this.countries[k].country;
        lat = this.countries[k].lat;
        lng = this.countries[k].lng;
        zoom = this.countries[k].zoom;
      }
    }


    for (j in this.allStores) {
      if (num === this.allStores[j].country) {

        stores.push({
          id: this.allStores[j].id,
          country: this.allStores[j].country,
          name: this.allStores[j].name,
          address: this.allStores[j].address,
          lat: this.allStores[j].lat,
          lng: this.allStores[j].lng,
          zoom: this.allStores[j].zoom,
          isFavourite: this.allStores[j].isFavourite
        });
      }
    }

    this.renderLocations(this.map, stores, lat, lng, zoom);
    this.summary = `${country} - ${this.locations.length} Apple Stores`;
  }


  onMarkerAdded(marker) {
    this.markers.push(marker);
  }


  removeMapMarkers() {
    this.markers.length = 0;
    this.map.clear();
  }


  filterLocationsByCountry(byCountry) {
    this.removeMapMarkers();
    this.scrollToTopOfScreen();
    this.renderLocationsByCountry(byCountry);
    this.displayLocationFilters();
  }


  filterLocationsByNearest(byNearest) {
    this.removeMapMarkers();
    this.scrollToTopOfScreen();
    this.determineNearestLocations(byNearest);
    this.displayLocationFilters();
  }


  determineNearestLocations(range) {
    let j,
      currentGeoLat = this.coords.lat,
      currentGeoLng = this.coords.lng,
      stores = [],
      rangeToSearch = Number(range),
      zoom = 4;

    for (j in this.allStores) {
      var storeLat = this.allStores[j].lat,
        storeLng = this.allStores[j].lng,
        distance = this.DIST.calculateDistanceInKilometres(currentGeoLat, currentGeoLng, storeLat, storeLng);

      // If range is less than or equal to distance returned then push following into array?
      if (distance <= rangeToSearch) {
        stores.push(
          {
            id: this.allStores[j].id,
            country: this.allStores[j].country,
            name: this.allStores[j].name,
            address: this.allStores[j].address,
            lat: this.allStores[j].lat,
            lng: this.allStores[j].lng,
            zoom: this.allStores[j].zoom,
            isFavourite: this.allStores[j].isFavourite,
            distance: distance
          });
      }

    }

    this.renderLocations(this.map, stores, currentGeoLat, currentGeoLng, zoom);
    this.summary = `${stores.length} Apple Stores found within ${rangeToSearch} Km of your location`;
  }


  scrollTheScreen() {
    this.content.scrollTo(0, 300, 750);
  }


  scrollToTopOfScreen() {
    this.content.scrollTo(0, 0, 750);
  }


  storeNotification(message): void {
    let notification = this.toastCtrl.create({
      message: message,
      duration: 3000
    });
    notification.present();
  }


  displayLocationFilters() {
    this.scrollTheScreen();
    this.displayFilters = !this.displayFilters;
    if (this.displayFilters) {
      this.filtersText = "Hide these filters";
    }
    else {
      this.filtersText = "Display Filters";
    }
  }


}

