
window.onload = function() {
  var mapContainer = document.getElementById('mapContainer');
  
  var options = {
    center: new google.maps.LatLng(21.447956,-158.031464),
    zoom: 10,
    mapTypeId: google.maps.MapTypeId.ROADMAP,

    mapTypeControl: true,
    mapTypeControlOptions: {
      style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
      position: google.maps.ControlPosition.TOP_LEFT,
      mapTypeIds: [
        google.maps.MapTypeId.ROADMAP,
        google.maps.MapTypeId.SATELLITE
      ]
    },
    navigationControlOptions: {
      style: google.maps.NavigationControlStyle.SMALL,
      position: google.maps.ControlPosition.TOP_RIGHT
    },    
    streetViewControl: true,
    scaleControl: true
    
  };
  
  var map = new google.maps.Map(mapContainer, options);
}