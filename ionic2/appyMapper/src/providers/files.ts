import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class Files {

  private countries: any = [];
  private locations: any = [];

  constructor(public http: Http) {
  }


  /**
   *
   * Load & parse Countries data
   *
   * @public
   * @method loadCountries
   * @return {none}
   *
   */
  loadCountries() {
    this.http.get('assets/data/countries.js')
      .map(res => res.json())
      .subscribe(countries => {

        for (var k in countries.countries) {
          this.countries.push({
            id: countries.countries[k].id,
            country: countries.countries[k].country,
            lat: countries.countries[k].lat,
            lng: countries.countries[k].lng,
            zoom: countries.countries[k].zoom,
            active: countries.countries[k].active
          });

        }
      });
  }


  /**
   *
   * Return parsed Countries data
   *
   * @public
   * @method getCountriesFromJSON
   * @return {Array}
   *
   */
  getCountriesFromJSON() {
    return this.countries;
  }


  /**
   *
   * Load & parse Store Locations data
   *
   * @public
   * @method loadLocations
   * @return {none}
   *
   */
  loadLocations() {
    this.http.get('assets/data/locations.js')
      .map(res => res.json())
      .subscribe(locations => {

        for (var k in locations.locations) {
          this.locations.push({
            id: locations.locations[k].id,
            country: locations.locations[k].country,
            name: locations.locations[k].name,
            address: locations.locations[k].address,
            lat: locations.locations[k].lat,
            lng: locations.locations[k].lng,
            zoom: locations.locations[k].zoom,
            active: locations.locations[k].active
          });

        }
      });
  }


  /**
   *
   * Return parsed Store Locations data
   *
   * @public
   * @method getLocationsFromJSON
   * @return {Array}
   *
   */
  getLocationsFromJSON() {
    return this.locations;
  }

}
