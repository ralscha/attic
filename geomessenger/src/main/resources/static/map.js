var map;

function initMap() {
	map = new google.maps.Map(document.getElementById('map'), {
		center: {
			lat: 48.1652756,
			lng: 6.1154695
		},
		zoom: 5
	});
}