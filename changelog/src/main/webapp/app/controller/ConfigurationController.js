Ext.define('Changelog.controller.ConfigurationController', {
	extend: 'Deft.mvc.ViewController',

	control: {
        
        view: {
			activated: 'onActivated',
			beforeclose: 'onBeforeClose',
			dirtychange: 'onDirtyChange'
		},

		saveButton: {
			selector: 'button[itemId=saveButton]',
			listeners: {
				click: 'onSave'
			}
		}
	},
	
	forEachField: function(visitorMethod){
		this.getView().getForm().getFields().each(visitorMethod);
	},

	onActivated: function() {
		this.getSaveButton().disable();
		configService.synchronize([], this.beenSynchronized, this);
	},
	
	beenSynchronized: function(values) {
		if(values) {
			var form = this.getView().getForm();
			var arrayLength = values.length;
			for (var i = 0; i < arrayLength; i+=2) {
				var field = form.findField(values[i]);
				if(field) {
					field.suspendEvents(false);
					field.setValue(values[i+1]);
					field.resumeEvents();
				}
			}
			// to clear dirty flag
			form.setValues(form.getValues());
		}
	},
	
	onSave: function() {
		this.getView().disable();
		var values = [];
		this.forEachField(function(field){
	        values.push(field.name);
			values.push(field.value);
	    });
		configService.synchronize(values, this.beenSaved, this);
	},

	beenSaved: function(values) {
		this.getView().enable();
		this.getView().getForm().setValues(this.getView().getForm().getValues());
		Changelog.ux.window.Notification.info(i18n.successful, i18n.config_saved);
	},
	
	onBeforeClose: function(panel) {
		if(this.getView().getForm().isDirty()) {
			Ext.Msg.confirm(i18n.config_dirty, i18n.config_confirmclose + '?', this.doClose, this);
			return false;
		}
		return true;
	},
	
	doClose: function(button) {
		if (button === 'yes') {
			var panel = this.getView();
			panel.purgeListeners();
			panel.destroy();
		}
	},
	
	onDirtyChange: function(v,b,c) {
		b ? this.getSaveButton().enable() : this.getSaveButton().disable();
    }
});