Ext.define('Golb.view.tag.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Golb.plugin.Clearable' ],
	stateful: true,
	stateId: 'view.tag.Grid',

	autoLoad: true,

	bind: {
		store: '{objects}',
		selection: '{selectedObject}'
	},

	listeners: {
		itemclick: 'onItemclick'
	},

	cls: 'shadow',

	columns: [ {
		text: 'Name',
		dataIndex: 'name',
		flex: 1,
		stateId: 'view.tag.Grid.name'
	}, {
		text: 'Number of Posts',
		width: 200,
		align: 'right',
		dataIndex: 'noOfPosts',
		defaultRenderer: function(value) {
			if (value !== 0) {
				return value;
			}
			return '';
		}
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			emptyText: 'Name',
			xtype: 'textfield',
			width: 250,
			plugins: [ {
				ptype: 'clearable'
			} ],
			listeners: {
				change: {
					fn: 'onNameFilter',
					buffer: 50
				}
			}
		}, '->', {
			text: i18n.create,
			iconCls: 'x-fa fa-plus',
			handler: 'newObject'
		} ]
	}, {
		xtype: 'toolbar',
		dock: 'bottom',
		padding: 0,
		items: [ {
			iconCls: 'x-fa fa-refresh',
			handler: 'onGridRefresh',
			cls: 'no-bg-button',
			tooltip: i18n.refresh
		}, {
			xtype: 'tbtext',
			bind: {
				text: '{totalCount}'
			}
		} ]
	} ]

});