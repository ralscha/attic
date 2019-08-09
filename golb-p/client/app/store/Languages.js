Ext.define('Golb.store.Languages', {
	extend: 'Ext.data.Store',
	storeId: 'languages',
	fields: [ 'value', 'text' ],

	data: [ {
		value: 'en',
		text: i18n.language_english
	} ]
});
