Ext.define('Starter.view.dummy.ViewModel', {
	extend: 'Ext.app.ViewModel',
	requires: [ 'Starter.view.dummy.Model' ],

	data: {
		companySelection: null
	},

	stores: {
		companies: {
			model: 'Starter.view.dummy.Model',
			autoLoad: true,
			pageSize: 0,
			listeners: {
				load: 'onCompaniesStoreLoad'
			}
		}
	}

});