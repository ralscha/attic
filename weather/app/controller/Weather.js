Ext.define('Weather.controller.Weather', {
	extend: 'Deft.mvc.ViewController',
	inject: [ 'weatherStore', 'weatherAPI', 'defaultCountry', 'messageBus' ],

	observe: {
		messageBus: {
			newlocation: 'onNewLocation'
		}
	},

	control: {
		refreshWeather: {
			tap: 'onRefreshWeatherTap'
		}
	},

	config: {
		weatherStore: null,
		weatherAPI: null,
		defaultCountry: null,
		messageBus: null
	},

	init: function() {
		this.doWeatherLoad();
	},

	onRefreshWeatherTap: function() {
		this.doWeatherLoad();
	},

	onNewLocation: function() {
		this.doWeatherLoad();
	},

	doWeatherLoad: function() {
		var me = this;
		var currentPos = false;
		var storedLocation = '&q=' + this.getDefaultCountry();

		var location = localStorage.getItem('location');
		var currentLocation = localStorage.getItem('currentLocation');

		if (location) {
			storedLocation = '&q=' + location;
		} else if (currentLocation) {
			if (navigator.geolocation) {
				currentPos = true;
				navigator.geolocation.getCurrentPosition(function(pos) {			
					me.updateWeather('&lat=' + pos.coords.latitude + '&lon=' + pos.coords.longitude);					
				}, Ext.emptyFn, {
					enableHighAccuracy: true,
					timeout: 10 * 1000 * 1000,
					maximumAge: 0
				});
			}
		}

		if (!currentPos) {
			me.updateWeather(storedLocation);
		}

	},
	
	updateWeather: function(location) {
		var mystore = this.getWeatherStore().setProxy({
			url: this.getWeatherAPI() + location
		});

		this.getView().setStore(mystore);

		mystore.load();
	}
	

});