export class Distances {

  convertDegreesToRadians(degree) {
    return degree * (Math.PI / 180);
  }

  calculateDistanceInKilometres(latitudeOne, longitudeOne, latitudeTwo, longitudeTwo) {
    let radiusOfEarth = 6371, // Radius of the earth in km
      degreesLat = this.convertDegreesToRadians(latitudeTwo - latitudeOne),
      degreesLon = this.convertDegreesToRadians(longitudeTwo - longitudeOne),
      calculationOne = Math.sin(degreesLat / 2) * Math.sin(degreesLat / 2),
      calculationTwo = Math.cos(this.convertDegreesToRadians(latitudeOne)) * Math.cos(this.convertDegreesToRadians(latitudeTwo)),
      calculationThree = Math.sin(degreesLon / 2) * Math.sin(degreesLon / 2),
      sumTotal = calculationOne + calculationTwo * calculationThree,
      finalSum = 2 * Math.atan2(Math.sqrt(sumTotal), Math.sqrt(1 - sumTotal)),
      actualDistance = radiusOfEarth * finalSum;

    return actualDistance;
  }

}
