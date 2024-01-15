Ext.define('Weather.view.Weather', {
	extend: 'Ext.List',
	requires: [ 'Ext.TitleBar', 'Weather.store.Weather', 'Weather.controller.Weather' ],
	controller: 'Weather.controller.Weather',
	xtype: 'weather',

	config: {
		title: 'Weather',
		iconCls: 'info',
		disableSelection: true,

		items: [ {
			docked: 'top',
			xtype: 'toolbar',
			title: 'Weather',
			items: [ {
				xtype: 'spacer'
			}, {
				xtype: 'button',
				cls: 'refreshWeather',
				iconCls: 'refresh',
				itemId: 'refreshWeather'
			} ]
		} ],

		/* markup for the data returned from the store */
		itemTpl: Ext.create('Ext.XTemplate', '<div class="">', '<h2>{name}, {sys.country}</h2>', '<p>Time: {[this.timeFormat(new Date())]}</p>',
				'<p>Weather: {[values.weather[0].description]}</p>', '<img src="http://openweathermap.org/img/w/{[values.weather[0].icon]}"/>',

				'<p>Temperature: {main.temp} &#8451;</p>', '<p>Humidity: {main.humidity} %</p>', '<p>Min Temp: {main.temp_min} &#8451;</p>',
				'<p>Max Temp: {main.temp_max} &#8451;</p>', '</div>', {
					timeFormat: function(date) {
						return Ext.Date.dayNames[date.getDay()] + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
					}
				})
	}
});