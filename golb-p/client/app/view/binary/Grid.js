Ext.define('Golb.view.binary.Grid', {
	extend: 'Ext.grid.Panel',

	stateful: true,
	stateId: 'view.binary.Grid',

	autoLoad: true,

	bind: {
		store: '{objects}',
		selection: '{selectedObject}'
	},

	listeners: {
		beforeitemclick: function(view, record, item, index, event) {
			var colIdx = event.getTarget('.x-grid-cell').cellIndex;
			return colIdx !== 0 && colIdx !== 1;
		},
		itemclick: 'onItemclick'
	},

	cls: 'shadow',

	columns: [ {
        text: '',
        width: 40,
        xtype: 'widgetcolumn',
        sortable: false,
		hideable: false,
        widget: {
            xtype: 'button',
            ui: 'small',
            iconCls: 'x-fa fa-download',
			tooltip: 'Download',
			handler: 'onDownloadClick'
        }
    }, {
        text: '',
        width: 40,
        xtype: 'widgetcolumn',
        sortable: false,
		hideable: false,
        widget: {
            xtype: 'button',
            ui: 'small',
            iconCls: 'x-fa fa-clipboard',
			tooltip: 'Copy URL to Clipboard',
			handler: 'onCopyUrlClick'
        }
    }, {
		text: 'Filename',
		dataIndex: 'name',
		flex: 1,
		stateId: 'view.binary.Grid.name'
	}, {
		text: 'Size',
		dataIndex: 'size',
		flex: 1,
		stateId: 'view.binary.Grid.size',
		defaultRenderer: function(value) {
			if (value) {
				return Golb.Util.filesize(value);
			}
			return value;
		}
	}, {
		text: 'Type',
		dataIndex: 'type',
		flex: 1,
		stateId: 'view.binary.Grid.type'
	}, {
		xtype: 'datecolumn',
		format: 'Y-m-d H:i:s',
		text: 'Last Modified',
		dataIndex: 'lastModifiedDate',
		width: 170,
		stateId: 'view.user.Grid.lastModifiedDate'
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			emptyText: 'Filter',
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