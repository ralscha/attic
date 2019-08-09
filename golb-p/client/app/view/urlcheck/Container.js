Ext.define('Golb.view.urlcheck.Container', {
	extend: 'Ext.grid.Panel',

	controller: {
		xclass: 'Golb.view.urlcheck.Controller'
	},

	viewModel: {
		xclass: 'Golb.view.urlcheck.ViewModel'
	},

	stateful: false,
	stateId: 'view.urlcheck.Grid',

	autoLoad: false,

	bind: {
		store: '{objects}'
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
			tooltip: 'Open Link',
			handler: 'onOpenUrlClick'
		},
		stateId: 'view.urlcheck.Grid.widget'
	}, {
		text: 'URL',
		dataIndex: 'url',
		flex: 1
	}, {
		text: 'Post',
		dataIndex: 'post',
		flex: 1
	}, {
		text: 'Code',
		dataIndex: 'status',
		width: 85
	}, {
		text: 'Status',
		width: 85,
		dataIndex: 'successful',
		align: 'center',
		renderer: function(stati, md, record) {
			if (record.get('successful')) {
				return '<span class="label label-success">OK</span>';
			}
			else {
				return '<span class="label label-error">Error</span>';
			}
		},
		stateId: 'view.user.Grid.successful'
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ '->', {
			text: 'Check now',
			iconCls: 'x-fa fa-check',
			handler: 'onCheckNowClick'
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
		} ]
	} ]
});