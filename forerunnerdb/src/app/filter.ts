export interface Filter {

  mag: {
    lower: number,
    upper: number
  };

  depth: {
    lower: number,
    upper: number
  };

  distance: {
    lower: number,
    upper: number
  };

  time: string;

  sort: 'mag' | 'depth' | 'distance' | 'time';

  myLocation: {
    latitude: number;
    longitude: number
  };

}
