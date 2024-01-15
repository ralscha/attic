Ext.define('Starter.view.accesslog.OsPanel', {
	extend: 'Ext.panel.Panel',
	requires: [ 'Starter.view.accesslog.OsPie' ],

	layout: {
		type: 'fit'
	},

	title: i18n.accesslog_operatingSystems,

	items: [ {
		xclass: 'Starter.view.accesslog.OsPie',
		requireechart: [ 'echarts', 'echarts/chart/pie' ],
		reference: 'ospie'
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
			reference: 'osYearsCombobox',
			valueField: 'year',
			displayField: 'year',
			queryMode: 'local',
			forceSelection: true,
			listeners: {
				change: 'onOsYearsCBChange'
			}
		} ]
	} ]

});