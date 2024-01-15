Ext.define('Starter.view.accesslog.UaPanel', {
	extend: 'Ext.panel.Panel',
	requires: [ 'Starter.view.accesslog.UaBar' ],

	layout: {
		type: 'fit'
	},

	title: i18n.accesslog_userAgents,

	items: [ {
		xclass: 'Starter.view.accesslog.UaBar',
		requireechart: [ 'echarts', 'echarts/chart/bar' ],
		reference: 'uabar'
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			xtype: 'combobox',
			fieldLabel: i18n.accesslog_userAgent_year,
			allowBlank: false,
			labelWidth: 40,
			bind: {
				store: '{accessLogYears}'
			},
			reference: 'uaYearsCombobox',
			valueField: 'year',
			displayField: 'year',
			queryMode: 'local',
			forceSelection: true,
			listeners: {
				change: 'onUaYearsCBChange'
			}
		} ]
	} ]

});