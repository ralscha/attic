Ext.define('Weather.controller.Location', {
	extend: 'Deft.mvc.ViewController',
	inject: 'messageBus',
	
	control: {
		saveButton: {
			tap: 'onSaveButtonTap'
		},
		location: true,
		currentLocation: {
			check: 'onCheckCurrentLocation',
			uncheck: 'onUncheckCurrentLocation'
		}
	},
	
	
	config: {
		messageBus: null
	},

	onCheckCurrentLocation: function() {
		this.getLocation().setValue(null);
		this.getLocation().disable();
	},
	
	onUncheckCurrentLocation: function() {
		this.getLocation().enable();
	},	
	
	init: function() {
		this.getView().setValues({
			location: localStorage.getItem('location'),
			currentLocation: localStorage.getItem('currentLocation')			
		});
		
		if (!navigator.geolocation) {
			this.getCurrentLocation().disable();
		}
	},

	
	onSaveButtonTap: function() {
		var values = this.getView().getValues();
		
		if (values.location) {
			localStorage.setItem('location', values.location);
		} else {
			localStorage.removeItem('location');
		}
		
		if (values.currentLocation) {
			localStorage.setItem('currentLocation', values.currentLocation);
		} else {
			localStorage.removeItem('currentLocation');
		}
		
		this.getMessageBus().fireEvent('newlocation');

	},

	currentLocation: function() {

		Ext.Msg.alert('Status', 'To Do');

	}
});