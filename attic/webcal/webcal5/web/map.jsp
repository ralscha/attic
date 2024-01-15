<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Map</title>  
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=${ContactForm.googleApiKey}" type="text/javascript"></script>
          
		<script type="text/javascript">
		  //<![CDATA[
		  var map;
	    function load() {
	      if (GBrowserIsCompatible()) {
	        map = new GMap2(document.getElementById("googleMap"));	
	        map.enableScrollWheelZoom();
	        map.addControl(new GSmallMapControl());
	        map.addControl(new GMapTypeControl());
	       
	        var latlng = new GLatLng(${param.lat}, ${param.lng});	
	        map.setCenter(latlng, 16);
	        map.setMapType(G_SATELLITE_MAP);
	       
	        var marker = new GMarker(latlng)
				  map.addOverlay(marker);
				}			    
	    }
	    //]]>
    </script>          
  </head>

  <body onload="load()" onunload="GUnload()">
    <div id="googleMap" style="width: 600px; height: 400px;"></div>   
  </body>
</html>

