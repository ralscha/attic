Ext.define('Golb.view.feedback.Grid', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Golb.plugin.Clearable' ],
	stateful: true,
	stateId: 'view.feedback.Grid',

	autoLoad: true,

	bind: {
		store: '{objects}',
		selection: '{selectedObject}'
	},

	cls: 'shadow',

    features: [{
        ftype: 'rowbody',
        getAdditionalData: function (data, idx, record, orig) {
            return {
                rowBody: '<div style="padding: 0 0 0 1em">' + record.get("body").linkify() + '</div>'
            };
        }
    }],
	
	columns: [ {
		xtype: 'datecolumn',
		format: 'Y-m-d H:i',
		text: 'Created',
		dataIndex: 'created',
		width: 150,
		stateId: 'view.feedback.Grid.created'
	}, {
		text: 'Post',
		dataIndex: 'postTitle',
		flex: 2,
		stateId: 'view.feedback.Grid.postTitle'
	}, {
		text: 'Email',
		dataIndex: 'replyEmailAddress',
		flex: 1,
		stateId: 'view.feedback.Grid.replyEmailAddress'
	}, {
        text: '',
        width: 40,
        xtype: 'widgetcolumn',
        sortable: false,
		hideable: false,
        widget: {
            xtype: 'button',
            ui: 'small',
            iconCls: 'x-fa fa-trash-o',
			tooltip: 'Delete Feedback',
			handler: 'onDeleteClick'
        }
    } ],

	dockedItems: [ {
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