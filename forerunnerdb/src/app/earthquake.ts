export class Earthquake {

  time: number;
  place: string;
  mag: number;
  depth: number;
  distance?: number;
  latLng: [number, number];

  constructor(geoJson: any) {
    this.time = geoJson.properties.time;
    this.place = geoJson.properties.place;
    this.mag = geoJson.properties.mag;
    this.depth = geoJson.geometry.coordinates[2];
    this.latLng = [geoJson.geometry.coordinates[1], geoJson.geometry.coordinates[0]];
  }
}
