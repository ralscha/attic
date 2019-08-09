Ext.define('Golb.view.post.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Golb.plugin.Clearable' ],
	stateful: true,
	stateId: 'view.post.Grid',

	autoLoad: true,

	bind: {
		store: '{objects}',
		selection: '{selectedObject}'
	},

	listeners: {
		beforeitemclick: function(view, record, item, index, event) {
			var colIdx = event.getTarget('.x-grid-cell').cellIndex;
			return colIdx !== 0 && colIdx !== 6 && colIdx !== 7;
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
            iconCls: 'x-fa fa-external-link',
			tooltip: 'Open Post',
			handler: 'onOpenPostClick'
        }
    }, {
		text: 'Title',
		dataIndex: 'title',
		flex: 1,
		stateId: 'view.post.Grid.title'
	}, {
		text: 'Tags',
		dataIndex: 'tags',
		flex: 1,
		stateId: 'view.post.Grid.tags',
		renderer: 'tagsRenderer'
	}, {
		xtype: 'datecolumn',
		format:'Y-m-d H:i',
		text: 'Created',
		dataIndex: 'created',
		width: 160
	}, {
		xtype: 'datecolumn',
		format:'Y-m-d H:i',
		text: 'Updated',
		dataIndex: 'updated',
		width: 160
	}, {
		xtype: 'datecolumn',
		format:'Y-m-d H:i',
		text: 'Published',
		dataIndex: 'published',
		width: 160
	}, {
        text: '',
        width: 40,
        xtype: 'widgetcolumn',
        sortable: false,
		hideable: false,
        widget: {
            xtype: 'button',
            ui: 'small',
            iconCls: 'x-fa fa-envelope-open-o',
			tooltip: 'Publish Post',
			handler: 'onPublishClick',
			bind: {
				hidden: '{record.published}'
			}
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
            iconCls: 'x-fa fa-envelope-o',
			tooltip: 'Unpublish Post',
			handler: 'onUnpublishClick',
			bind: {
				hidden: '{!record.published}'
			}
        }
    } ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			emptyText: 'Title, Body',
			xtype: 'textfield',
			width: 250,
			plugins: [ {
				ptype: 'clearable'
			} ],
			listeners: {
				change: {
					fn: 'onTitleFilter',
					buffer: 50
				}
			}
		}, {
			xtype: 'tagfield',
			emptyText: 'Tags',
			bind: {
				store: '{tags}'
			},
			minWidth: 300,
			displayField: 'name',
			valueField: 'id',
			queryMode: 'local',
			forceSelection: true,
			filterPickList: true,
			plugins: [ {
				ptype: 'clearable'
			} ],
			listeners: {
				change: 'onTagChange'
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