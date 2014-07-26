Ext.define('E4ds.controller.Config', {
	extend: 'Ext.app.Controller',

	views: [ 'config.Edit' ],
	stores: [ 'LogLevels' ],

	refs: [ {
		ref: 'logLevelCB',
		selector: 'configedit combobox[name=logLevel]'
	} ],

	init: function() {

		this.control({
			'configedit': {
				activate: Ext.bind(loggingEventService.getCurrentLevel, this, [ this.showCurrentLevel, this ]),
				add: Ext.bind(loggingEventService.getCurrentLevel, this, [ this.showCurrentLevel, this ])
			}
		});
	},

	showCurrentLevel: function(logLevel) {
		var cb = this.getLogLevelCB();
		cb.setValue(logLevel);
		cb.addListener('change', this.logLevelChange);
	},

	logLevelChange: function(field, newValue, oldValue) {
		loggingEventService.changeLogLevel(newValue);
		Ext.ux.window.Notification.info(i18n.successful, i18n.config_loglevelchanged);
	}

});
