Ext.define('Packt.view.staticData.Actors', {
	extend: 'Packt.view.staticData.AbstractGrid',
	alias: 'widget.actorsgrid',
	requires: ['Packt.store.staticData.Actors'],
	store: 'staticData.Actors',

	columns: [ {
		text: 'Id',
		width: 100,
		dataIndex: 'id',
		filter: {
			type: 'numeric'
		}
	}, {
		text: 'First Name',
		flex: 1,
		dataIndex: 'firstName',
		editor: {
			allowBlank: false,
			maxLength: 45
		},
		filter: {
			type: 'string'
		}
	}, {
		text: 'Last Name',
		width: 200,
		dataIndex: 'lastName',
		editor: {
			allowBlank: false,
			maxLength: 45
		},
		filter: {
			type: 'string'
		}
	} ]
});