
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
      pic: 'http://upload.wikimedia.org/wikipedia/commons/4/48/TE-Collage_London.png',
      wikipedia: 'http://en.wikipedia.org/wiki/London'
    },
    {
      name: 'Paris',
      position: new google.maps.LatLng(48.856667,2.350987),
      pic: 'http://upload.wikimedia.org/wikipedia/en/thumb/4/40/Paris_Collage.png/250px-Paris_Collage.png',
      wikipedia: 'http://en.wikipedia.org/wiki/Paris'
    },
    {
      name: 'Berlin',
      position: new google.maps.LatLng(52.523405,13.4114),
      pic: 'http://upload.wikimedia.org/wikipedia/commons/thumb/f/f7/Berlin_skyline_2009wl2.jpg/270px-Berlin_skyline_2009wl2.jpg',
      wikipedia: 'http://en.wikipedia.org/wiki/Berlin'
    }        
  ];
  
  var infoWindow;
  
  cities.forEach(function(element, index, array) {
   
    var marker = new google.maps.Marker({
      position: element.position,
      map: map,
      title: element.name
    });    
    
    google.maps.event.addListener(marker, 'click', function() {
      
      if (!infoWindow) {
        infoWindow = new google.maps.InfoWindow();
      }
      var content = '<h3>' + element.name + '</h3><img src="' + element.pic + '" />' +
        '<p><a href="'+element.wikipedia+'" target="_blank">Wikipedia</a></p>';
      infoWindow.setContent(content);            
      infoWindow.open(map, marker);
    });
  });
  

  
  
}