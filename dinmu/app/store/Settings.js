Ext.define('Dinmu.store.Settings', {
	extend: 'Ext.data.Store',
	requires: [ 'Dinmu.model.Setting' ],
	config: {
		model: 'Dinmu.model.Setting',
		autoLoad: true
	}
});