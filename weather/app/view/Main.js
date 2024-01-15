Ext.define('Weather.view.Main', {
	requires: [ 'Weather.view.About', 'Weather.view.Weather', 'Weather.controller.Main', 'Weather.view.Location' ],
	controller: 'Weather.controller.Main',
    extend: 'Ext.tab.Panel',

    config: {
        tabBarPosition: 'bottom',
		defaults: {
			styleHtmlContent: true
                    },
		items: [ {
			xtype: 'weather'
		}, {
			xtype: 'location'
		}, {
			xtype: 'about'
		} ]
    }
});
