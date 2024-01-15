Ext.define('Changelog.controller.CustomerEditController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		documentsGrid: {
			itemclick: 'enableActionBar',
			itemdblclick: 'downloadDocument',
			blur: 'disableActionBar'
		},
		deleteButton: {
			click: 'deleteDocument'
		},
		downloadButton: {
			click: 'downloadDocument'
		},
		featureCheckboxes: {
			selector: '#features > checkbox',
			listeners: {
				change: 'onFeatureCheckboxesChange'
			}
		},
		featuresKey: true,
		shortName: true,
		copyButton: true
	},
	
	featuresEnabled: true,
		
	disableCheckboxEvents: function() {
		this.featuresEnabled = false;
	},
	
	enableCheckboxEvents: function() {
		this.featuresEnabled = true;
	},

	updateFeaturesKey: function(result) {
		this.getFeaturesKey().setValue(result);
	},

	onFeatureCheckboxesChange: function(field, newValue) {
		var me = this;
		var values = [];
		if(me.featuresEnabled) {
			values.push('customer');
			values.push(me.getShortName().getValue().toUpperCase());
			Ext.Array.forEach(me.getFeatureCheckboxes(), function(cb) {
				if (cb.checked) {
					values.push(cb.name);
				}
			});
			featuresService.feature(values, me.updateFeaturesKey, me);
		}
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
	},
	
	enableActionBar: function(button, record) {
		this.getDeleteButton().enable();
		this.getDownloadButton().enable();
	},
	
	disableActionBar: function(button, record) {
		this.getDeleteButton().disable();
		this.getDownloadButton().disable();
	},
	
	deleteDocument: function(button) {
		var record = this.getDocumentsGrid().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.documents_delete + '?', i18n.delete_confirm + ' ' + record.data.fileName,
					this.afterConfirmDeleteDocument, this);
		}		
	},
	
	afterConfirmDeleteDocument: function(btn) {
		if (btn === 'yes') {
			var documentsGrid = this.getDocumentsGrid();
			var record = documentsGrid.getSelectionModel().getSelection()[0];
			if (record) {
				documentsGrid.getStore().remove(record);
				this.disableActionBar();
				Changelog.ux.window.Notification.info(i18n.successful, i18n.documents_deleted);
			}
		}
	},

	downloadDocument: function(button) {
		var documentsGrid = this.getDocumentsGrid();
		var record = documentsGrid.getSelectionModel().getSelection()[0];
		Ext.create('Ext.form.Panel', {
			renderTo: Ext.getBody(),
			standardSubmit: true,
			method: 'GET',
			url: 'customerFileDownload'
		}).submit({
			target: '_new',
			params: {
				'fileId': record.data.id
			}
		});
	}
});