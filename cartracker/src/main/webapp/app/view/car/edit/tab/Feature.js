/**
 * Main panel for displaying {@link CarTracker.model.option.Feature} options for {@link CarTracker.model.Car} records
 */
Ext.define('CarTracker.view.car.edit.tab.Feature', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.car.edit.tab.feature',
	layout: 'form',
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			items: [ {
				xtype: 'itemselectorfield',
				name: 'featureIds',
				anchor: '100%',
				height: 500,
				store: {
					type: 'option.feature'
				},
				displayField: 'longName',
				valueField: 'id',
				allowBlank: true,
				msgTarget: 'side',
				fromTitle: 'Available Features',
				toTitle: 'Selected Features',
				buttons: [ 'add', 'remove' ],
				delimiter: null
			} ]
		});
		me.callParent(arguments);
	}
});