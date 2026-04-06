
window.onload = function() {
  var mapContainer = document.getElementById('mapContainer');
  
  var options = {
    center: new google.maps.LatLng(47.635784,4.96582),
    zoom: 4,
    mapTypeId: google.maps.MapTypeId.SATELLITE,   
  };
  
  var map = new google.maps.Map(mapContainer, options);
  
  var cities = [
    {
      name: 'London',
      position: new google.maps.LatLng(51.49079,-0.10746),
      info: 'Bewohner: 7,556,900'
    },
    {
      name: 'Paris',
      position: new google.maps.LatLng(48.856667,2.350987),
      info: 'Bewohner: 2,193,031'
    },
    {
      name: 'Berlin',
      position: new google.maps.LatLng(52.523405,13.4114),
      info: 'Bewohner: 3,439,100'
    }        
  ];
  
  cities.forEach(function(element, index, array) {
   
    var marker = new google.maps.Marker({
      position: element.position,
      map: map,
      title: element.name
    });    
    
    var infoWindow = new google.maps.InfoWindow({
      content: element.info
    });
    
    google.maps.event.addListener(marker, 'click', function() {
      infoWindow.open(map, marker);
    });
  });
  

  
  
}