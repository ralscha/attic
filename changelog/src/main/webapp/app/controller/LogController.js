Ext.define('Changelog.controller.LogController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		exportButton: true,
		logChanges: true,
		logCustomers: {
			itemclick: 'populateCustomerBuilds'
		},
		logCustomerBuilds: {
			itemclick: 'populateChanges'
		},
		view: {
			activated: 'onActivated'	
		}
	},
	
	populateCustomerBuilds: function(button, record) {
		
		this.customerId = record.data.id;
		
		this.getExportButton().setParams({
			customerId: this.customerId
		});
		
		this.getLogCustomerBuilds().getStore().load({
			params: {
				customerId: this.customerId
			},
			callback: function(records, operation, success) {
				this.getLogChanges().getStore().removeAll();
				this.getExportButton().disable();
			},
			scope: this

		});
	},

	populateChanges: function(button, record) {
		var params = {};
		if (record.data.id) {
			params.customerBuildId = record.data.id;
		} else {
			params.customerId = this.customerId;
		}
		
		if (record.data.id) {
			this.getExportButton().setParams(params);
		}
		
		this.getExportButton().enable();

		this.getLogChanges().getStore().load({
			params: params
		});
	},

	onActivated: function(cmp, options) {
		this.getLogCustomerBuilds().getStore().removeAll();
		this.getLogChanges().getStore().removeAll();
		this.getExportButton().disable();
	}

});
