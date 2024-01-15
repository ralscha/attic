Ext.define('Changelog.controller.CustomerController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated',
			itemdblclick: 'editCustomerFromDblClick',
			itemclick: 'enableActions'			
		},
		addButton: {
			click: 'createCustomer'
		},
		editButton: {
			click: 'editCustomerFromButton'
		},
		versionButton: {
			click: 'editCustomerBuildsFromButton'
		},
		deleteButton: {
			click: 'deleteCustomer'
		},
		filterField: {
			filter: 'handleFilter'
		},
		features: {
			filter: 'handleFeatureFilter'
		}
	},	
	
	handleFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('filter', newValue);
		} else {
			myStore.clearFilter();
		}
		this.toggleEditButtons(false);
	},

	handleFeatureFilter: function(field, newValue) {
		var myStore = this.getView().getStore();
		if (newValue) {
			myStore.clearFilter(true);
			myStore.filter('featurefilter', newValue);
		} else {
			myStore.clearFilter();
		}
		this.toggleEditButtons(false);
	},

	editCustomerFromButton: function() {
		this.editCustomer(this.getView().getSelectionModel().getSelection()[0]);
	},
	
	editCustomerFromDblClick: function(grid, record) {
		this.editCustomer(record);
	},

	editCustomer: function(record) {
		var editWindow = Ext.create('Changelog.view.customer.Edit');
		var form = editWindow.getForm();
		editWindow.controller = this;

		var editController = editWindow.getController();
		
		editController.disableCheckboxEvents();
		form.loadRecord(record);
		editController.enableCheckboxEvents();

		editWindow.down('gridpanel', 'documentsGrid').getStore().load({
			params: {
				customerId: record.data.id
			}
		});
		
		editController.onFeatureCheckboxesChange(null, null);
	},

	editCustomerBuildsFromDblClick: function(grid, record) {
		this.editBuilds(record);
	},

	editCustomerBuildsFromButton: function() {
		this.editBuilds(this.getView().getSelectionModel().getSelection()[0]);
	},

	editBuilds: function(record) {
		var customerBuildsWin = Ext.create('Changelog.view.customer.Builds');
		customerBuildsWin.getController().updateBuilds(this, record.data.id, record.data.longName);
	},
	
	createCustomer: function() {
		var editWindow = Ext.create('Changelog.view.customer.Edit');
		editWindow.controller = this;

		editWindow.getForm().isValid();
		editWindow.getForm().setValues({installation: 'JAR'});
	},

	deleteCustomer: function(button) {
		var record = this.getView().getSelectionModel().getSelection()[0];
		if (record) {
			Ext.Msg.confirm(i18n.customer_delete + '?', i18n.delete_confirm + ' ' + record.data.shortName,
					this.afterConfirmDeleteCustomer, this);
		}
	},

	afterConfirmDeleteCustomer: function(btn) {
		if (btn === 'yes') {
			var record = this.getView().getSelectionModel().getSelection()[0];
			if (record) {
				this.getView().getStore().remove(record);
				this.doGridRefresh();				
				Changelog.ux.window.Notification.info(i18n.successful, i18n.customer_deleted);
			}
		}
	},

	enableActions: function(button, record) {
		this.toggleEditButtons(true);
	},
		
	toggleButton: function(enable, button) {
		if (enable) {
			button.enable();
		} else {
			button.disable();
		}
	},
	
	toggleEditButtons: function(enable) {
		this.toggleButton(enable, this.getDeleteButton());
		this.toggleButton(enable, this.getEditButton());
		this.toggleButton(enable, this.getVersionButton());
	},

	updateCustomer: function(editWindow) {
		var form = editWindow.getForm(), record = form.getRecord(), values = form.getValues();

		if (form.isValid()) {
			if (record) {
				record.set(values);
			} else {
				var newCustomer = Ext.create('Changelog.model.Customer', values);
				this.getView().getStore().add(newCustomer);
			}
			this.doGridRefresh();			
			this.toggleEditButtons(false);

			editWindow.close();
			Changelog.ux.window.Notification.info(i18n.successful, i18n.customer_saved);
		}
	},

	onActivated: function() {
		this.doGridRefresh();		
	},

	doGridRefresh: function() {
		this.getView().getStore().load();		
		this.toggleEditButtons(false);
	}

});