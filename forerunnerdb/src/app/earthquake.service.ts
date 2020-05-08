import {Injectable} from '@angular/core';
import {Earthquake} from './earthquake';
import * as ForerunnerDB from 'forerunnerdb';
import * as geolib from 'geolib';
import {Filter} from './filter';

@Injectable({
  providedIn: 'root'
})
export class EarthquakeService {

  private static readonly FOURTYFIVE_MINUTES = 30 * 60 * 1000;
  private static readonly ONE_HOUR = 60 * 60 * 1000;
  private static readonly ONE_DAY = 24 * 60 * 60 * 1000;
  private static readonly SEVEN_DAYS = 7 * 24 * 60 * 60 * 1000;
  private static readonly THIRTY_DAYS = 30 * 24 * 60 * 60 * 1000;

  private fdb: any;
  private collection: any;

  constructor() {
    this.fdb = new ForerunnerDB();
    const db = this.fdb.db('earthquake');

    this.collection = db.collection('events');

    db.load(err => console.log(err));
  }

  async init(): Promise<void> {
    if (!navigator.onLine) {
      return;
    }

    const lastUpdate = localStorage.getItem('lastUpdate');
    if (lastUpdate) {
      const lastUpdateTs = parseInt(lastUpdate, 10);
      const now = Date.now();
      if (lastUpdateTs + EarthquakeService.SEVEN_DAYS < now) {
        // database older than 7 days. load the 30 days file
        await this.loadData('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson');
      } else if (lastUpdateTs + EarthquakeService.ONE_DAY < now) {
        // database older than 1 day. load the 7 days file
        await this.loadData('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson');
      } else if (lastUpdateTs + EarthquakeService.ONE_HOUR < now) {
        // database older than 1 hour. load the 1 day file
        await this.loadData('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson');
      } else if (lastUpdateTs + EarthquakeService.FOURTYFIVE_MINUTES < now) {
        // database older than 45 minutes. load the 1 hour file
        await this.loadData('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson');
      }
    } else {
      // no last update. load the 30 days file
      await this.loadData('https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_month.geojson');
    }

    this.deleteOldRecords();
  }

  filter(filter: Filter) {
    const findFilter: any = {};
    if (!(filter.mag.lower === -1 && filter.mag.upper === 10)) {
      findFilter.mag = {
        '$gte': filter.mag.lower,
        '$lte': filter.mag.upper
      };
    }

    if (filter.time !== '-1') {
      const now = new Date();
      now.setHours(now.getHours() - parseInt(filter.time, 10));
      findFilter.time = {'$gte': now};
    }

    if (!(filter.depth.lower === -10 && filter.depth.upper === 800)) {
      findFilter.depth = {
        '$gte': filter.depth.lower,
        '$lte': filter.depth.upper
      };
    }

    let orderBy: any = null;
    switch (filter.sort) {
      case 'time':
        orderBy = {
          $orderBy: {
            time: -1
          }
        };
        break;
      case 'mag':
        orderBy = {
          $orderBy: {
            mag: -1
          }
        };
        break;
      case 'depth':
        orderBy = {
          $orderBy: {
            depth: 1
          }
        };
        break;
      default:
    }

    const result = this.collection.find(findFilter, orderBy);

    let filtered: Earthquake[] = [];

    const hasDistanceFilter = !(filter.distance.lower === 0 && filter.distance.upper === 20000);

    if (hasDistanceFilter || filter.sort === 'distance') {
      result.forEach(r => {
        const distanceInKilometers = geolib.getDistance(
          {latitude: r.latLng[0], longitude: r.latLng[1]},
          {latitude: filter.myLocation.latitude, longitude: filter.myLocation.longitude}) / 1000;

        if (hasDistanceFilter) {
          if (filter.distance.lower <= distanceInKilometers && distanceInKilometers <= filter.distance.upper) {
            r.distance = distanceInKilometers;
            filtered.push(r);
          }
        } else {
          r.distance = distanceInKilometers;
          filtered.push(r);
        }
      });
    } else {
      filtered = result;
    }

    if (filter.sort === 'distance') {
      return filtered.sort((a, b) => a.distance - b.distance);
    }

    return filtered;

  }

  private deleteOldRecords() {
    const thirtyDaysAgo = Date.now() - EarthquakeService.THIRTY_DAYS;

    this.collection.remove({
      time: {
        '$lte': thirtyDaysAgo
      }
    });
  }

  private async loadData(dataUrl) {
    const response = await fetch(dataUrl);
    const earthquakesJson = await response.json();

    const earthquakes: Earthquake[] = earthquakesJson.features.map(entry => new Earthquake(entry));

    return new Promise((resolve, reject) => {
      this.collection.insert(earthquakes, () => {
        this.collection.save();
        localStorage.setItem('lastUpdate', Date.now().toString());
        resolve();
      });
    });
  }
}
