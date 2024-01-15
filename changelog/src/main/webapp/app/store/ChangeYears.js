Ext.define('Changelog.store.ChangeYears', {
	extend: 'Ext.data.Store',
	autoLoad: false,
	fields: [ {
		name: 'year',
		type: 'int'
	} ],
	proxy: {
		type: 'direct',
		directFn: reportService.loadChangeYears
	}
});
