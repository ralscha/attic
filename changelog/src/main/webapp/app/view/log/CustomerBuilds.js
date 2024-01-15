Ext.define('Changelog.view.log.CustomerBuilds', {
	extend: 'Ext.grid.Panel',
	itemId: 'logCustomerBuilds',
	
	title: i18n.customer_build_versions,
	hideHeaders: true,
	
	initComponent: function() {		
		var me = this;
		me.store = Ext.create('Changelog.store.LogCustomerBuilds');
		me.columns = [ {
			dataIndex: 'versionNumber',
			flex: 1,
			text: i18n.version,
			hideable: false,
			sortable: false,
			renderer: function(value, metadata, record) {
				if (record.get('internalBuild')) {
					return value + " (I)";
				}
				return value;
			}
		}, {
			dataIndex: 'versionDate',
			flex: 1,
			text: i18n.version_date,
			renderer: Ext.util.Format.dateRenderer('d.m.Y'),
			hideable: false,
			sortable: false
		} ];

		me.callParent(arguments);
	}
});