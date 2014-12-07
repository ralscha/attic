Ext.define('Packt.view.film.SearchCategory', {
	extend: 'Packt.view.sakila.SearchWindow',
	alias: 'widget.searchcategory',
	height: 470,
	requires: [ 'Ext.ux.form.MultiSelect' ],

	title: 'Add Category',

	items: [ {
		xtype: 'form',
		itemId: 'filmForm',
		autoScroll: true,
		bodyPadding: 10,
		items: [ {
			xtype: 'label',
			text: 'Hold Ctrl or Command to select more than one Category.'
		}, {
			anchor: '100%',
			xtype: 'multiselect',
			msgTarget: 'side',
			fieldLabel: 'Categories',
			name: 'multiselect',
			id: 'multiselect-field',
			allowBlank: false,
			store: {
				type: 'categories',
				autoLoad: true
			},
			valueField: 'id',
			displayField: 'name',
			ddReorder: true
		} ]
	} ]
});