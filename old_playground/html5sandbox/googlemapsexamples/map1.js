
window.onload = function() {
  var mapContainer = document.getElementById('mapContainer');
  
  var options = {
    center: new google.maps.LatLng(21.447956,-158.031464),
    zoom: 10,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  
  var map = new google.maps.Map(mapContainer, options);
}