Ext.define('BitP.view.lieferant.List', {
	extend: 'Ext.grid.Panel',
	requires: [ 'BitP.controller.Lieferant', 'BitP.ux.form.field.FilterField' ],
	controller: 'BitP.controller.Lieferant',

	title: 'Lieferanten',
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('BitP.store.Lieferanten');

		me.columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg=='
			} ]
		}, {
			text: 'Firma',
			dataIndex: 'firma',
			flex: 1
		}, {
			text: 'Zusatz',
			dataIndex: 'zusatz',
			flex: 1
		}, {
			text: 'Strasse',
			dataIndex: 'strasse',
			flex: 1
		}, {
			text: 'PLZ',
			dataIndex: 'plz',
			flex: 1
		}, {
			text: 'Ort',
			dataIndex: 'ort',
			flex: 1
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.create,
				itemId: 'createButton',
				glyph: 0xe807
			}, '->', {
				itemId: 'filterField',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store
		} ];

		me.callParent(arguments);

	}

});