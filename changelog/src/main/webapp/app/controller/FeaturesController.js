Ext.define('Changelog.controller.FeaturesController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		featureCheckboxes: {
			selector: 'checkbox',
			listeners: {
				change: 'onFeatureCheckboxesChange'
			}
		},
		featuresKey: true,
		copyButton: true
	},

	onFeatureCheckboxesChange: function(field, newValue) {
		var values = [];
		Ext.Array.forEach(this.getFeatureCheckboxes(), function(cb) {
			if (cb.checked) {
				values.push(cb.name);
			}
		});
		featuresService.feature(values, this.updateFeaturesKey, this);
	},

	updateFeaturesKey: function(result) {
		this.getFeaturesKey().setValue(result);
	},

	onActivated: function() {
		var me = this;
		var clip = new ZeroClipboard(this.getCopyButton().getEl().dom);
		clip.on('dataRequested', function(client, args) {
			clip.setText(me.getFeaturesKey().getValue());

		});
		clip.on('complete', function(client, args) {
			Changelog.ux.window.Notification.info(i18n.successful, i18n.tool_features_clipboardcopied);
		});

		Ext.Array.forEach(this.getFeatureCheckboxes(), function(cb) {
			cb.suspendEvents(false);
			cb.setValue(false);
			cb.resumeEvents();
		});
		featuresService.feature([], this.updateFeaturesKey, this);
	}

});