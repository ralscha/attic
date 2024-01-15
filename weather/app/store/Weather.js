Ext.define('Weather.store.Weather', {
	extend: 'Ext.data.Store',
	requires: [ 'Weather.model.Weather', 'Ext.data.proxy.JsonP' ],
		
	config: {
		model: 'Weather.model.Weather',
		
		proxy: {
			type: 'jsonp',
			reader: {
				type: 'json'
			}
		}
	}
});