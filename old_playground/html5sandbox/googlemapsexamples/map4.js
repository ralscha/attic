
window.onload = function() {
  var mapContainer = document.getElementById('mapContainer');
  
  var options = {
    center: new google.maps.LatLng(47.635784,4.96582),
    zoom: 4,
    mapTypeId: google.maps.MapTypeId.SATELLITE,
    
  };
  
  var map = new google.maps.Map(mapContainer, options);
  
  var londonMarker = new google.maps.Marker({
    position: new google.maps.LatLng(51.49079,-0.10746),
    map: map,
    title: 'London'
  });
  
  var parisMarker = new google.maps.Marker({
    position: new google.maps.LatLng(48.856667,2.350987),
    map: map,
    title: 'Paris'
  });
  
  var berlinMarker = new google.maps.Marker({
    position: new google.maps.LatLng(52.523405,13.4114),
    map: map,
    title: 'Berlin'
  });
  
  
}