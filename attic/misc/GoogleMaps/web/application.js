var centerLatitude = 37.4419;
var centerLongitude = -122.1419;
var startZoom = 12;

var map;

function init() {
  if (GBrowserIsCompatible()) {
    map = new GMap2(document.getElementById("map"));    
    map.enableScrollWheelZoom();
    map.addControl(new GSmallMapControl());
    map.addControl(new GMapTypeControl());
    map.setCenter(new GLatLng(centerLatitude, centerLongitude), startZoom);
    
    listMarkers();
    
    //allow the user to click the map to create a marker
    GEvent.addListener(map, "click", 
                      function(overlay, latlng) {
                        if (latlng == null) {
                          return;
                        }

												//create an HTML DOM form element
												var inputForm = document.createElement("form");
												inputForm.setAttribute("action","");
												inputForm.onsubmit = function() {createMarker(); return false;};
												//retrieve the longitude and lattitude of the click point
												var lng = latlng.lng();
												var lat = latlng.lat();
												inputForm.innerHTML = '<fieldset style="width:150px;">'
												+ '<legend>New Marker</legend>'
												+ '<label for="found">Found</label>'
												+ '<input type="text" id="found" name="m[found]" style="width:100%;"/>'
												+ '<label for="left">Left</label>'
												+ '<input type="text" id="left" name="m[left]" style="width:100%;"/>'
												+ '<label for="left">Icon URL</label>'
												+ '<input type="text" id="icon" name="m[icon]" style="width:100%"/>'												
												+ '<input type="submit" value="Save"/>'
												+ '<input type="hidden" id="longitude" name="m[lng]" value="'+ lng +'"/>'
												+ '<input type="hidden" id="latitude" name="m[lat]" value="' + lat + '"/>'
												+ '</fieldset>';
												map.openInfoWindow (latlng,inputForm);

                      });
    
  }
} 


function createMarker(){
  var lng = document.getElementById("longitude").value;
  var lat = document.getElementById("latitude").value;
  var found = document.getElementById("found").value;
  var left = document.getElementById("left").value;
  var icon = document.getElementById("icon").value;
  
  Seam.Component.getInstance("markerUpdater").update(lng, lat, found, left, icon,  addNewMarkerToMap);                
}
	
function addNewMarkerToMap(result) {

  var latlng = new GLatLng(result.lat,result.lng);
  var marker = addMarkerToMap(latlng, result.info, result.icon);
  map.addOverlay(marker);
  map.closeInfoWindow();
}

function listMarkers() {
  Seam.Component.getInstance("markerUpdater").list(addMarkersToMap); 
}

function addMarkersToMap(markers) {

  for (var i = 0 ; i < markers.length ; i++) {
    var marker = markers[i];
		var lat = marker.lat;
		var lng = marker.lng;
    var iconImage = marker.icon;
    var latlng = new GLatLng(lat,lng);
		var mark = addMarkerToMap(latlng, marker.info, iconImage);
		map.addOverlay(mark);
  }
}
  
function addMarkerToMap(latlng, html, iconImage) {
	if(iconImage != null && iconImage != '') {
		var icon = new GIcon();
		icon.image = iconImage;
		icon.iconSize = new GSize(100, 50);
		icon.iconAnchor = new GPoint(14, 25);
		icon.infoWindowAnchor = new GPoint(14, 14);
		var marker = new GMarker(latlng,icon);
	} else {
		var marker = new GMarker(latlng);
	}  
  
  
  GEvent.addListener(marker, 'click', function() {
    var markerHTML = html;
    marker.openInfoWindowHtml(markerHTML);
  });
  return marker;
}  
    
window.onload = init;
window.onunload = GUnload;