import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {SQLite} from 'ionic-native';
import 'rxjs/add/operator/map';
import {Files} from '../providers/files';

@Injectable()
export class Database {

  public data: any = null;
  public storage: any = null;
  public dbName: string = "appyMapper.db";
  public locations: any = [];
  public countries: any = [];

  constructor(public http: Http,
              public files: Files) {
    this.data = null;
  }

  createDatabase() {
    this.storage = new SQLite();
    this.storage.openDatabase({
      name: this.dbName,
      location: 'default' // the location field is required
    })
      .then((data) => {
          console.log("Opened database");
          this.createDatabaseTables();
        },
        (err) => {
          console.error('Unable to open database: ', err);
        });
  }

  createDatabaseTables() {
    this.createCountriesTable();
    this.createStorelocationsTable();
  }

  createCountriesTable() {
    this.storage.executeSql('CREATE TABLE IF NOT EXISTS countries (id INTEGER PRIMARY KEY AUTOINCREMENT, country TEXT NOT NULL, lat TEXT NOT NULL, lng TEXT NOT NULL, zoom INTEGER NOT NULL, active TEXT NOT NULL)', {})
      .then((data) => {
          console.log("countries TABLE CREATED : " + JSON.stringify(data));
        },
        (error) => {
          console.log("Error: " + JSON.stringify(error.err));
          console.dir(error);
        });
  }


  createStorelocationsTable() {
    this.storage.executeSql('CREATE TABLE IF NOT EXISTS storeLocations (id INTEGER PRIMARY KEY AUTOINCREMENT, country INTEGER NOT NULL, name TEXT NOT NULL, address TEXT NOT NULL, lat TEXT NOT NULL, lng TEXT NOT NULL, zoom INTEGER NOT NULL, active TEXT NOT NULL)', {})
      .then((data) => {
          console.log("storeLocations TABLE CREATED: " + JSON.stringify(data));
        },
        (error) => {
          console.log("Error: " + JSON.stringify(error.err));
          console.dir(error);
        });
  }

  retrieveStorelocations() {
    return new Promise(resolve => {
      this.storage.executeSql("SELECT * FROM storeLocations WHERE active='Y'", {})
        .then((data) => {
            this.locations = [];
            if (data.rows.length > 0) {

              let k;
              for (k = 0; k < data.rows.length; k++) {
                this.locations.push({
                  country: data.rows.item(k).country,
                  name: data.rows.item(k).name,
                  address: data.rows.item(k).address,
                  lat: data.rows.item(k).lat,
                  lng: data.rows.item(k).lng,
                  zoom: data.rows.item(k).zoom
                });
              }
            }
            else {
              var numLocations = this.files.getLocationsFromJSON(),
                isFavourite = "N",
                k;


              for (k = 0; k < numLocations.length; k++) {
                this.locations.push({
                  country: numLocations[k].country,
                  name: numLocations[k].name,
                  address: numLocations[k].address,
                  lat: numLocations[k].lat,
                  lng: numLocations[k].lng,
                  zoom: numLocations[k].zoom,
                  active: numLocations[k].active
                });
              }

              this.insertStoreLocationsToTable(numLocations, isFavourite);
            }
            resolve(this.locations);
          },
          (error) => {
            console.log("Error retrieving locations from retrieveStoreLocations: " + JSON.stringify(error));

          });
    });
  }

  insertStoreLocationsToTable(val, isFavourite) {

    var i,
      sql = ' INSERT INTO storeLocations(country, name, address, lat, lng, zoom, active) VALUES';

    for (i = 0; i < val.length; i++) {
      sql = sql + '("' + val[i].country + '", "' + val[i].name + '", "' + val[i].address + '", "' + val[i].lat + '", "' + val[i].lng + '", ' + val[i].zoom + ', "' + val[i].active + '")';


      if (i < (val.length - 1)) {
        sql = sql + ',';
      }
    }

    this.storage.executeSql(sql, {})
      .then((data) => {
          //console.log("storeLocations TABLE INSERTED RECORDS ");
        },
        (error) => {
          console.log("Error: " + JSON.stringify(error.err));
        });
  }

  retrieveCountries() {
    return new Promise(resolve => {
      this.storage.executeSql("SELECT * FROM countries WHERE active='Y'", {})
        .then((data) => {
            this.countries = [];

            if (data.rows.length > 0) {
              let k;

              for (k = 0; k < data.rows.length; k++) {
                this.countries.push({
                  id: data.rows.item(k).id,
                  country: data.rows.item(k).country,
                  lat: data.rows.item(k).lat,
                  lng: data.rows.item(k).lng,
                  zoom: data.rows.item(k).zoom,
                  active: data.rows.item(k).active
                });
              }
            }
            else {
              //console.log("No data found in countries table");
              let numCountries = this.files.getCountriesFromJSON(),
                k;

              for (k = 0; k < numCountries.length; k++) {
                if (numCountries[k].isActive !== "N") {
                  this.countries.push({
                    id: numCountries[k].id,
                    country: numCountries[k].country,
                    lat: numCountries[k].lat,
                    lng: numCountries[k].lng,
                    zoom: numCountries[k].zoom,
                    active: numCountries[k].active
                  });
                }
              }

              this.insertCountriesToTable(numCountries);
            }
            resolve(this.countries);
          },
          (error) => {
            console.log("Error retrieving countries from retrieveCountries: " + JSON.stringify(error));

          });
    });
  }

  insertCountriesToTable(val) {
    var i,
      sql = ' INSERT INTO countries(country, lat, lng, zoom, active) VALUES';

    for (i = 0; i < val.length; i++) {
      sql = sql + '("' + val[i].country + '", "' + val[i].lat + '", "' + val[i].lng + '", ' + val[i].zoom + ', "' + val[i].active + '")';


      if (i < (val.length - 1)) {
        sql = sql + ',';
      }
    }

    this.storage.executeSql(sql, {})
      .then((data) => {
          //console.log("countries TABLE INSERTED RECORDS ");
        },
        (error) => {
          console.log("Error: " + JSON.stringify(error.err));
        });
  }

}
